package com.dot.ai.commonservice.models;

import com.dot.ai.commonservice.enums.ResponseCodeEnum;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
@Data
public class FundsTransferBaseModel<T> {

    private String responseCode;

    private String sessionId;

    private String responseMessage;

    private T data;

    public FundsTransferBaseModel<T> setResponseCodeAndMessage(ResponseCodeEnum respCodeEnum){
        if (respCodeEnum == null){
            return this;
        }
        setResponseCode(respCodeEnum.getRespCode());
        setResponseMessage(respCodeEnum.getRespMsg());
        return this;
    }
}
