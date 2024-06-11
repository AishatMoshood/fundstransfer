package com.dot.ai.http.controller.service.serviceimpl;

import com.alibaba.fastjson2.JSON;
import com.dot.ai.commonservice.enums.ResponseCodeEnum;
import com.dot.ai.commonservice.enums.TransactionStatusEnum;
import com.dot.ai.commonservice.models.FundsTransferBaseModel;
import com.dot.ai.commonservice.models.PaymentModel;
import com.dot.ai.commonservice.param.PaymentParam;
import com.dot.ai.commonservice.service.EncryptionService;
import com.dot.ai.domain.biz.model.DailySummaryModel;
import com.dot.ai.domain.biz.model.GetTransactionsModel;
import com.dot.ai.domain.biz.param.NameEnquiryParam;
import com.dot.ai.domain.biz.service.FundsTransferService;
import com.dot.ai.http.controller.annotation.PreVerify;
import com.dot.ai.http.controller.constants.FundsTransferControllerConstant;
import com.dot.ai.http.controller.request.PaymentRequest;
import com.dot.ai.http.controller.response.FundsTransferResponse;
import com.dot.ai.http.controller.service.FundsTransferControllerService;
import io.micrometer.common.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Slf4j
@RestController
@RequestMapping("/fundstransfer/v1/direct/")
public class
FundsTransferController implements FundsTransferControllerService {

    private final EncryptionService encryptionService;

    private final FundsTransferService fundsTransferService;

    public FundsTransferController(EncryptionService encryptionService,
                                   FundsTransferService fundsTransferService) {
        this.encryptionService = encryptionService;
        this.fundsTransferService = fundsTransferService;
    }

    /**
     * Transfer between two financial institutions
     * @param request encryptedData,includes account number,amount,transaction id
     * @return encryptedData includes full name,kyc level,bvn etc. for both beneficiary and sender
     */
    @PreVerify
    @PostMapping("/payment")
    @ApiOperation("Payment/FundsTransfer")
    @Override
    public FundsTransferResponse payment(@RequestBody PaymentRequest request,
                                                           @RequestHeader("Authorization") String key) {
        FundsTransferBaseModel<PaymentModel> result;
        try {
            String requestBody = encryptionService.aesDecrypt(request.getData(), key);
            if (StringUtils.isBlank(requestBody)){
                return decryptionFailed(key);
            }
            result = fundsTransferService.payment(
                    JSON.parseObject(requestBody, PaymentParam.class), key);
        } catch (Exception e){
            log.error("payment request error,request:{}",request,e);
            return decryptionFailed(key);

        }
        return new FundsTransferResponse(encryptionService.aesEncrypt(JSON.toJSONString(result), key));
    }

    /**
     * Query transaction by optional param
     * @param accountNumber
     * @param minAmount
     * @param startDate
     * @param endDate
     * @param status
     * @param key
     * @param pageable
     *
     * @return paginated transactions by param
     */
    @PreVerify
    @GetMapping("/transactions")
    @ApiOperation("ReQuery")
    @Override
    public FundsTransferResponse getTransactions(
            @RequestParam Optional<String> accountNumber,
            @RequestParam Optional<BigDecimal> minAmount,
            @RequestParam Optional<Date> startDate,
            @RequestParam Optional<Date> endDate,
            @RequestParam Optional<TransactionStatusEnum> status,
            @RequestHeader("Authorization") String key,
            @PageableDefault(size = 10) @SortDefault.SortDefaults({
                    @SortDefault(sort = "timestamp", direction = Sort.Direction.DESC)
            }) Pageable pageable) {
        FundsTransferBaseModel<Page<GetTransactionsModel>> result;
        try {
            result = fundsTransferService.getTransactions(
                    accountNumber, minAmount, startDate, endDate, status, pageable);
        }catch (Exception e){
            log.error("customer validation error,request:{}", e);
            return decryptionFailed(key);

        }
        return new FundsTransferResponse(encryptionService.aesEncrypt(JSON.toJSONString(result), key));
    }


    /**
     * Daily transactions summary
     * @return encryptedData,includes totalTransactions, totalTransactionFee
     */
    @PreVerify
    @GetMapping("/dailysummary")
    @ApiOperation("Daily Summary")
    @Override
    public FundsTransferResponse generateDailyTransactionSummary(@RequestParam Date date,
                                                                 @RequestHeader("Authorization") String key) {
        FundsTransferBaseModel<DailySummaryModel> result;
        try {
            result = fundsTransferService.generateDailySummary(date);
        }catch (Exception e){
            log.error("daily error,request:{}", date,e);
            return decryptionFailed(key);
        }
        return new FundsTransferResponse(encryptionService.aesEncrypt(JSON.toJSONString(result), key));
    }


    private FundsTransferResponse decryptionFailed(String key){
        Map<String,String> result = new HashMap<>();
        result.put(FundsTransferControllerConstant.FAILED_RESPONSE_CODE, ResponseCodeEnum.F07.getRespCode());
        result.put(FundsTransferControllerConstant.FAILED_RESPONSE_MESSAGE,ResponseCodeEnum.F07.getRespMsg());
        return new FundsTransferResponse(encryptionService.aesDecrypt(JSON.toJSONString(result), key));
    }

}