package com.dot.ai.common.enums;


/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
public enum TransactionStatusEnum {

    /**
     * transaction status
     */
    PENDING(3),
    SUCCESS(1),
    FAIL(2),
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
        return this == SUCCESS || this == FAIL;
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
