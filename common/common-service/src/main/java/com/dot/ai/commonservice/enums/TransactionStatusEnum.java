package com.dot.ai.commonservice.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
public enum TransactionStatusEnum {

    /**
     * transaction status
     */
    PENDING(3),
    SUCCESSFUL(1),
    FAILED(2),
    INIT(0)


    ;

    @EnumValue
    private final int code;

    TransactionStatusEnum(int code) {
        this.code = code;
    }


    public int getCode() {
        return code;
    }

    public boolean isFinal() {
        return this == SUCCESSFUL || this == FAILED;
    }

    public static TransactionStatusEnum getStatus(int status) {
        for (TransactionStatusEnum value : values()) {
            if (value.getCode() == status) {
                return value;
            }
        }
        return null;
    }
}
