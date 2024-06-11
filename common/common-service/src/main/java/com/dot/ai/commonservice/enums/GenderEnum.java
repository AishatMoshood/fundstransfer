package com.dot.ai.commonservice.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;


/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
public enum GenderEnum {

    /**
     * gender
     */
    FEMALE("F"),

    MALE("M"),

    OTHER("0"),
    ;

    @EnumValue
    private final String code;

    GenderEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static GenderEnum getGender(String gender) {
        for (GenderEnum value : values()) {
            if (value.getCode().equals(gender)) {
                return value;
            }
        }
        return null;
    }
}
