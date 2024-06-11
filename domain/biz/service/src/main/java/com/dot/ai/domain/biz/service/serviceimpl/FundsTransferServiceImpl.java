package com.dot.ai.domain.biz.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.dot.ai.commonservice.enums.ResponseCodeEnum;
import com.dot.ai.commonservice.enums.TransactionStatusEnum;
import com.dot.ai.commonservice.models.BeneficiaryInfo;
import com.dot.ai.commonservice.models.FundsTransferBaseModel;
import com.dot.ai.commonservice.models.SenderInfo;
import com.dot.ai.domain.biz.model.DailySummaryModel;
import com.dot.ai.domain.biz.model.NameEnquiryModel;
import com.dot.ai.commonservice.models.PaymentModel;
import com.dot.ai.domain.biz.model.GetTransactionsModel;
import com.dot.ai.domain.biz.param.NameEnquiryParam;
import com.dot.ai.commonservice.param.PaymentParam;
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
import static java.lang.StringUTF16.compareTo;

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
    public FundsTransferBaseModel<NameEnquiryModel> nameEnquiry(NameEnquiryParam param) {
        FundsTransferBaseModel<NameEnquiryModel> serviceModel = new FundsTransferBaseModel<>();
        try {
            if(!validateRequest(param, serviceModel)){
                return serviceModel;
            }
            NameEnquiryModel nameEnquiryModel = verifyAccount(param);
            if (nameEnquiryModel == null){
                return serviceModel.setResponseCodeAndMessage(ResponseCodeEnum.F03);
            }
            serviceModel.setData(nameEnquiryModel);
            serviceModel.setResponseCodeAndMessage(ResponseCodeEnum.SCS01);
        } catch (Exception e) {
            log.error("name enquiry exception, request body: {}", param, e);
            serviceModel.setResponseCodeAndMessage(ResponseCodeEnum.F11);
        }
        log.info("customer validation, request: {},result: {}", param, JSON.toJSONString(serviceModel));
        return serviceModel;
    }


    @Override
    public FundsTransferBaseModel<PaymentModel> payment(PaymentParam param) {
        PaymentModel response = new PaymentModel();
        FundsTransferBaseModel<PaymentModel> result = new FundsTransferBaseModel<>();

        try {
            //save record
            TransactionOrder transactionOrder = fundsTransferRepService.save(param);

            //params check
            if (!validatePaymentInformation(param)){
                return result.setResponseCodeAndMessage(ResponseCodeEnum.F15);
            }

            if(!verifyAccount(param, result)){
                return result;
            }


            result.setData(response);
            return result.setResponseCodeAndMessage(ResponseCodeEnum.SCS01);
        } catch (DuplicateKeyException e){
            result.setResponseCodeAndMessage(ResponseCodeEnum.F10);
            log.error("deposit failed with duplicate session id :{}",param.getSessionId(),e);
        } catch (Exception e) {
            log.warn("execute transfer inward exception sessionId = {}", param.getSessionId(), e);
            result.setResponseCodeAndMessage(ResponseCodeEnum.P03);
        } finally {
            // update the status of the order(can not update the database if the request is duplicate)
            paymentRepModel = commonManager.updateResponseCodeAndStatus(null, result.getResponseCode(),
                             result.getResponseCode(), param.getSessionId());
            if (paymentRepModel == null && !ResponseCodeEnum.F10.getRespCode().equals(result.getResponseCode())){
                log.error("update response code and status failed,param:{}",param);
                result.setResponseCodeAndMessage(ResponseCodeEnum.F11);
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
            Optional<TransactionStatusEnum> status,
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
            result.setResponseCodeAndMessage(ResponseCodeEnum.F11);
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
                result.setResponseCodeAndMessage(ResponseCodeEnum.F11);
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
            result.setResponseCodeAndMessage(ResponseCodeEnum.F11);
        }

        return result;
    }


    private Boolean validateRequest(NameEnquiryParam param, FundsTransferBaseModel<NameEnquiryModel> serviceModel){
        if (param == null) {
            log.info("account verification failed with invalid request body");
            serviceModel.setResponseCodeAndMessage(ResponseCodeEnum.F14);
            return false;
        }

        if(param.getBeneficiaryInfo() == null ||
                StringUtils.isBlank(param.getBeneficiaryInfo().getBeneficiaryAccountNumber()) ||
                StringUtils.isBlank(param.getBeneficiaryInfo().getBeneficiaryBankCode())){
            log.info("invalid beneficiary");
            serviceModel.setResponseCodeAndMessage(ResponseCodeEnum.F15);
            return false;
        }

        return true;
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
           paymentModel.setResponseCodeAndMessage(ResponseCodeEnum.F15);
           return false;
        }

        return true;
    }

    private Boolean validatePaymentInformation(PaymentParam param) {
        String result = null;
        if (param.getBeneficiary() == null) {
            result = ParamErrorEnum.SESSION_ID_FORMAT_ERROR.getMsg();
        }
        if (param.getSender() == null) {
            result = ParamErrorEnum.SESSION_ID_FORMAT_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getTransactionId())) {
            result = ParamErrorEnum.BENEFICIARY_ACCOUNT_NUMBER_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryBvn())) {
            result = ParamErrorEnum.ORIGINATOR_ACCOUNT_NAME_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryAccountName())) {
            result = ParamErrorEnum.ORIGINATOR_ACCOUNT_NUMBER_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryAccountNumber())) {
            result = ParamErrorEnum.AMOUNT_ERROR.getMsg();
        }

        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryBankCode())) {
            result = ParamErrorEnum.AMOUNT_FORMAT_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryKycLevel())){
            result = ParamErrorEnum.AMOUNT_FORMAT_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getBeneficiary().getBeneficiaryBvn())){
            result = ParamErrorEnum.AMOUNT_FORMAT_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getSender().getSenderBvn())){
            result = ParamErrorEnum.AMOUNT_FORMAT_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getSender().getSenderAccountName())) {
            result = ParamErrorEnum.ORIGINATOR_ACCOUNT_NAME_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getSender().getSenderAccountNumber())) {
            result = ParamErrorEnum.ORIGINATOR_ACCOUNT_NUMBER_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getSender().getSenderBankCode())) {
            result = ParamErrorEnum.AMOUNT_ERROR.getMsg();
        }
        if (StringUtils.isBlank(param.getSender().getSenderKycLevel())) {
            result = ParamErrorEnum.AMOUNT_FORMAT_ERROR.getMsg();
        }
        if (param.getAmount().compareTo(BigDecimal.ZERO) <= 0 ){
            result = ParamErrorEnum.AMOUNT_FORMAT_ERROR.getMsg();
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
                                    Optional<TransactionStatusEnum> status){
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