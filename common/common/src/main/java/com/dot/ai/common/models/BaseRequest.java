package com.dot.ai.common.models;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class BaseRequest {

    @Valid
    @ApiModelProperty(required = true, value = "encrypted request body")
    @NotNull(message = "encrypted date should not be null")
    private String data;

}