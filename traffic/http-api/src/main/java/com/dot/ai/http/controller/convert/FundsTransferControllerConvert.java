package com.dot.ai.http.controller.convert;

import com.dot.ai.commonservice.models.FundsTransferBaseModel;
import com.dot.ai.domain.biz.model.NameEnquiryModel;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 11/06/2024
 */

@Data
public class FundsTransferControllerConvert {

    public static FundsTransferBaseModel convert(FundsTransferBaseModel<NameEnquiryModel> serviceModel){
        FundsTransferBaseModel baseModel = new FundsTransferBaseModel<>();
        baseModel.setResponseCode(serviceModel.getResponseCode());
        baseModel.setResponseMessage(serviceModel.getResponseMessage());
        baseModel.setSessionId(serviceModel.getSessionId());
        baseModel.setData(serviceModel.getData());

        return baseModel;
    }

}