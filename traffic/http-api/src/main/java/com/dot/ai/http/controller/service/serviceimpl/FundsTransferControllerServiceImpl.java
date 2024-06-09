package com.dot.ai.http.controller.service.serviceimpl;

import com.dot.ai.http.controller.request.NameEnquiryRequest;
import com.dot.ai.http.controller.request.PaymentRequest;
import com.dot.ai.http.controller.request.ReQueryRequest;
import com.dot.ai.http.controller.response.NameEnquiryResponse;
import com.dot.ai.http.controller.response.PaymentResponse;
import com.dot.ai.http.controller.response.ReQueryResponse;
import com.dot.ai.http.controller.service.FundsTransferControllerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Slf4j
@RestController
@RequestMapping("/fundstransfer/v1/direct/")
public class FundsTransferControllerServiceImpl implements FundsTransferControllerService {


    /**
     * Customer validation
     * @param request encryptedData,includes account number,request id
     * @return encryptedData,includes full name,kyc level,bvn etc.
     */
    @PostMapping("/nameenquiry")
    @ApiOperation("Customer validation")
    @Override
    public NameEnquiryResponse nameEnquiry(@RequestBody NameEnquiryRequest request) {
        return null;
    }

    /**
     * Transfer from between two financial institutions
     * @param request encryptedData,includes account number,amount,transaction id
     * @return encryptedData includes full name,kyc level,bvn etc. for both beneficiary and sender
     */
    @PostMapping("/payment")
    @ApiOperation("Payment/Transfer")
    @Override
    public PaymentResponse payment(@RequestBody PaymentRequest request) {
        return null;
    }


    /**
     * Query final status
     * @param request encryptedData,includes transaction id
     * @return encryptedData includes full name,kyc level,bvn etc. for both beneficiary and sender
     */
    @PostMapping("/requery")
    @ApiOperation("ReQuery")
    @Override
    public ReQueryResponse queryTransactionStatus(@RequestBody ReQueryRequest request) {
        return null;
    }

}