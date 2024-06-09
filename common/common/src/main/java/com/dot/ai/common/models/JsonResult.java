package com.dot.ai.common.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
@Data
public class JsonResult<T> {

    public static final String SUCCESS_CODE = "200";

    public static final String ERROR_CODE = "500";

    public static final String SUCCESS_MESSAGE = "success!";

    @ApiModelProperty(value = "status code", example = SUCCESS_CODE, required = true)
    private String code;

    @ApiModelProperty(value = "request success,true:success,false:failed", required = true)
    private Boolean success;

    @ApiModelProperty(value = "message", example = SUCCESS_MESSAGE, required = true)
    private String message;

    @ApiModelProperty(value = "other data")
    private T data;

    private JsonResult() {

    }

    public JsonResult(boolean isSuccess, String code, String message) {
        this.success = isSuccess;
        this.code = code;
        this.message = message;
    }

    public static <T> JsonResult<T> success() {
        return new JsonResult<T>(true, SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static <T> JsonResult<T> success(T data) {
        JsonResult<T> result = new JsonResult<T>(true, SUCCESS_CODE, SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> error(String message) {
        return new JsonResult<T>(false, ERROR_CODE, message);
    }

    public static <T> JsonResult<T> error(T data, String message) {
        JsonResult<T> result = new JsonResult<T>(false, ERROR_CODE, message);
        result.setData(data);
        return result;
    }

    public static <T> JsonResult<T> error(String message, String code) {
        JsonResult<T> result = new JsonResult<T>(false, code, message);
        result.setCode(code);
        return result;
    }
}
