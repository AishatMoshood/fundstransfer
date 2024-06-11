package com.dot.ai.domain.biz.service.serviceimpl;

import com.dot.ai.commonservice.enums.ResponseCodeEnum;
import com.dot.ai.commonservice.enums.StatusEnum;
import com.dot.ai.commonservice.models.FundsTransferBaseModel;
import com.dot.ai.commonservice.models.PaymentModel;
import com.dot.ai.commonservice.param.PaymentParam;
import com.dot.ai.domain.biz.model.DailySummaryModel;
import com.dot.ai.domain.biz.model.GetTransactionsModel;
import com.dot.ai.domain.biz.service.FundsTransferService;
import com.dot.ai.domain.repository.service.FundsTransferRepService;
import com.dot.ai.repository.entities.IndividualAccountInfo;
import com.dot.ai.repository.entities.TransactionOrder;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.dot.ai.domain.biz.convert.FundsTransferServiceConvert.convert;
import static com.dot.ai.domain.biz.convert.FundsTransferServiceConvert.convertToServiceModel;

/**
 * @author Aishat Moshood
 * @since 09/06/2024
 */

@Service
@Slf4j
public class FundsTransferServiceImpl implements FundsTransferService {

    private final FundsTransferRepService fundsTransferRepService;

    public FundsTransferServiceImpl(FundsTransferRepService fundsTransferRepService) {
        this.fundsTransferRepService = fundsTransferRepService;
    }

    @Override
    public FundsTransferBaseModel<PaymentModel> payment(PaymentParam param, String key) {
        PaymentModel response = new PaymentModel();
        FundsTransferBaseModel<PaymentModel> result = new FundsTransferBaseModel<>();
        String sessionId = null;

        try {
            //save record
            TransactionOrder transactionOrder = fundsTransferRepService.save(param, key);
            sessionId = transactionOrder.getSessionId();

            //params check
            if (!validatePaymentInformation(param)){
                return result.setResponseCodeAndMessage(ResponseCodeEnum.FA15);
            }

            if(!verifyAccount(param, result)){
                return result;
            }

            response = convert(transactionOrder);
            result.setData(response);
            return result.setResponseCodeAndMessage(ResponseCodeEnum.SCS01);
        } catch (DuplicateKeyException e){
            result.setResponseCodeAndMessage(ResponseCodeEnum.FA10);
            log.error("transfer failed with duplicate session id :{}",sessionId,e);
        } catch (Exception e) {
            log.warn("transfer pending = {}", sessionId, e);
            result.setResponseCodeAndMessage(ResponseCodeEnum.PEN03);
        } finally {
            // update the status of the order(can not update the database if the request is duplicated)
            TransactionOrder transactionOrder = updateResponseCodeAndStatus(null, result.getResponseCode(), sessionId);
            if (transactionOrder == null && !ResponseCodeEnum.FA10.getRespCode().equals(result.getResponseCode())){
                log.error("response code and status update failed,param:{}",param);
                result.setResponseCodeAndMessage(ResponseCodeEnum.FA11);
            }
        }
        return result;

    }

    @Override
    public FundsTransferBaseModel<Page<GetTransactionsModel>> getTransactions(
            Optional<String> accountNumber,
            Optional<BigDecimal> minAmount,
            Optional<Date> startDate,
            Optional<Date> endDate,
            Optional<StatusEnum> status,
            Pageable pageable) {
        FundsTransferBaseModel<Page<GetTransactionsModel>> result = new FundsTransferBaseModel<>();
        try {
            Page<TransactionOrder> transactionOrder = fundsTransferRepService.getTransactions(accountNumber,
                    minAmount, startDate, endDate, status, pageable);
            result.setData(convert(transactionOrder));

            Boolean areParamsValid = validateParams(accountNumber, minAmount, startDate, endDate, status);
            if(!areParamsValid){
                result.setResponseCodeAndMessage(ResponseCodeEnum.SCS02);
                return result;
            }

            result.setResponseCodeAndMessage(ResponseCodeEnum.SCS01);
        } catch (Exception e){
            log.error("error generating paginated transactions = {}", e);
            result.setResponseCodeAndMessage(ResponseCodeEnum.FA11);
        }
        return result;
    }

    @Override
    public FundsTransferBaseModel<DailySummaryModel> generateDailySummary(Date date){
        FundsTransferBaseModel<DailySummaryModel> result = new FundsTransferBaseModel<>();
        try {
            Date startOfDay = getTimeOfDay(date, 0, 0, 0,0);
            Date endOfDay = getTimeOfDay(date, 23, 59, 59, 999);
            List<TransactionOrder> transactions = fundsTransferRepService.generateDailySummary(startOfDay, endOfDay);
            if(transactions == null){
                log.error("error generating daily summary of transactions = {}", date);
                result.setResponseCodeAndMessage(ResponseCodeEnum.FA11);
                return result;
            }

            if(transactions.isEmpty()){
                log.info("no transactions found for = {}", date);
                result.setResponseCodeAndMessage(ResponseCodeEnum.SCS03);
                return result;
            }

            result.setResponseCodeAndMessage(ResponseCodeEnum.SCS01);
            result.setSessionId(UUID.randomUUID().toString());
            result.setData(convertToServiceModel(date, transactions));
        } catch (Exception e){
            log.error("error generating daily summary of transactions = {}",date, e);
            result.setResponseCodeAndMessage(ResponseCodeEnum.FA11);
        }

        return result;
    }


    private TransactionOrder updateResponseCodeAndStatus(StatusEnum status, String responseCode,
                                                         String sessionId) {
        log.info("update response code and status{}", responseCode, status);
        try {
            if (StringUtils.isBlank(sessionId)) {
                return null;
            }
            if (status == null) {
                status = ResponseCodeEnum.getStatus(responseCode);
            }
            if (!ResponseCodeEnum.FA10.getRespCode().equals(responseCode)) {
                return fundsTransferRepService.updateBySessionId(status, responseCode, sessionId);
            }
        } catch (Exception e) {
            log.error("update response code and status failed,sessionId:{},responseCode:{},status:{}",
                    sessionId, responseCode, status, e);
        }
        return null;
    }


    private Boolean verifyAccount(PaymentParam param, FundsTransferBaseModel<PaymentModel> paymentModel) {
        IndividualAccountInfo beneficiaryAccountInfo = fundsTransferRepService.getIndividual(
                                                      param.getBeneficiary().getBeneficiaryAccountNumber(),
                                                      param.getBeneficiary().getBeneficiaryBankCode(),
                                                      param.getBeneficiary().getBeneficiaryAccountName(),
                                                      param.getBeneficiary().getBeneficiaryBvn(),
                                                      param.getBeneficiary().getBeneficiaryKycLevel());

        IndividualAccountInfo senderAccountInfo = fundsTransferRepService.getIndividual(
                param.getSender().getSenderAccountNumber(),
                param.getSender().getSenderBankCode(),
                param.getSender().getSenderAccountName(),
                param.getSender().getSenderBvn(),
                param.getSender().getSenderKycLevel());

        if(beneficiaryAccountInfo == null || senderAccountInfo == null){
           paymentModel.setResponseCodeAndMessage(ResponseCodeEnum.FA15);
           return false;
        }

        return true;
    }

    private Boolean validatePaymentInformation(PaymentParam param) {
        String result = null;
        if (param.getBeneficiary() == null) {
            result = "Beneficiary is null";
        }
        if (param.getSender() == null) {
            result = "Sender is null";
        }
        if (StringUtils.isBlank(param.getTransactionId())) {
            result = "Transactionid is blank";
        }
        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryBvn())) {
            result = "Beneficiary bvn is null";
        }
        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryAccountName())) {
            result = "Beneficiary account name is null";
        }
        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryAccountNumber())) {
            result = "Beneficiary account number null";
        }
        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryBankCode())) {
            result = "Beneficiary bank code is null";
        }
        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryKycLevel())){
            result = "Beneficiary kyc level is null";
        }
        if (StringUtils.isBlank(param.getSender().getSenderBvn())){
            result = "sender bvn  is null";
        }
        if (StringUtils.isBlank(param.getSender().getSenderAccountName())) {
            result = "sender account name is null";
        }
        if (StringUtils.isBlank(param.getSender().getSenderAccountNumber())) {
            result = "sender account number is null";
        }
        if (StringUtils.isBlank(param.getSender().getSenderBankCode())) {
            result = "sender bank code is null";
        }
        if (StringUtils.isBlank(param.getSender().getSenderKycLevel())) {
            result = "sender kyc level is null";
        }
        if (param.getAmount().compareTo(BigDecimal.ZERO) < 100 ){
            result = "amount is less than 100";
        }

        if (StringUtils.isNotBlank(result)){
            log.error("parameter error, request:{},msg :{}",param,result);
            return false;
        }
        return true;
    }


    private Boolean validateParams( Optional<String> accountNumber,
                                    Optional<BigDecimal> minAmount,
                                    Optional<Date> startDate,
                                    Optional<Date> endDate,
                                    Optional<StatusEnum> status){
        if (accountNumber.isEmpty() || StringUtils.isBlank(accountNumber.get()) || minAmount.isEmpty() ||
            startDate.isEmpty() || endDate.isEmpty() || status.isEmpty()) {
            return false;
        }
        return true;
    }

    private Date getTimeOfDay(Date date, int hourOfDay, int minute, int sec, int ms) {
        if(date == null){
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, sec);
        calendar.set(Calendar.MILLISECOND, ms);
        return calendar.getTime();
    }

}