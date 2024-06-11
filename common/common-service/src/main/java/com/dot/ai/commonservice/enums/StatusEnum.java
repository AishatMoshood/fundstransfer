package com.dot.ai.commonservice.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
public enum StatusEnum {

    /**
     * transaction status
     */
    PENDING(3),
    SUCCESSFUL(1),
    FAILED(2),


    ;

    @EnumValue
    private final int code;

    StatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


    public static StatusEnum getStatus(int status) {
        for (StatusEnum value : values()) {
            if (value.getCode() == status) {
                return value;
            }
        }
        return null;
    }
}
