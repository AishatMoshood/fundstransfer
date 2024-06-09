package com.dot.ai.http.controller.service;

import com.dot.ai.http.controller.request.NameEnquiryRequest;
import com.dot.ai.http.controller.request.PaymentRequest;
import com.dot.ai.http.controller.request.ReQueryRequest;
import com.dot.ai.http.controller.response.NameEnquiryResponse;
import com.dot.ai.http.controller.response.PaymentResponse;
import com.dot.ai.http.controller.response.ReQueryResponse;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

public interface FundsTransferControllerService {


    /**
     * Customer validation
     * @param request encryptedData,includes account number,request id
     * @return encryptedData,includes full name,kyc level,bvn etc.
     */
    NameEnquiryResponse nameEnquiry(NameEnquiryRequest request);

    /**
     * Transfer from between two financial institutions
     * @param request encryptedData,includes account number,amount,transaction id
     * @return encryptedData includes full name,kyc level,bvn etc. for both beneficiary and sender
     */
    PaymentResponse payment(PaymentRequest request);

    /**
     * Query final status
     * @param request encryptedData,includes transaction id
     * @return encryptedData includes full name,kyc level,bvn etc. for both beneficiary and sender
     */
    ReQueryResponse queryTransactionStatus(ReQueryRequest request);

}
