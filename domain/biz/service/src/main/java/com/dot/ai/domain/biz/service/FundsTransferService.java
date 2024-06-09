package com.dot.ai.domain.biz.service;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
public interface FundsTransferService {

    /**
     * customer validation
     * @return  ciphertext
     */
    TransferInwardBaseModel<NameEnquiryModel> nameEnquiry(NameEnquiryParam param, ChannelEnum channel);

    /**
     * transfer
     * @return ciphertext
     */
    TransferInwardBaseModel<SingleCreditModel> paymentNotification(TransferInwardPaymentParam param,
                                                                   ChannelEnum channel);

    /**
     * query final status
     * @return ciphertext
     */
    QuerySingleModel queryTxnStatus(QuerySingleParam param, ChannelEnum channel);

}
