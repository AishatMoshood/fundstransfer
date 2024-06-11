package com.dot.ai.http.controller.response;

import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
@Data
public class FundsTransferResponse {

    /**
     * encrypted response body
     */
    private String data;

    public FundsTransferResponse(String data) {
        this.data = data;
    }
}
