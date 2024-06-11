package com.dot.ai.commonservice.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;


/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
public enum CommissionWorthyEnum {

    /**
     * gender
     */
    YES("YES"),

    NO("NO"),
    ;

    @EnumValue
    private final String code;

    CommissionWorthyEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CommissionWorthyEnum getCommissionWorthy(String commissionWorthy) {
        for (CommissionWorthyEnum value : values()) {
            if (value.getCode().equals(commissionWorthy)) {
                return value;
            }
        }
        return null;
    }
}
