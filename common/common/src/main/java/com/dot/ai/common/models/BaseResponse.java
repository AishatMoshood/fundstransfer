package com.dot.ai.common.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class BaseResponse {

    @ApiModelProperty(required = true, value = "encrypted response body")
    private String data;

}