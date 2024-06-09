package com.dot.ai.common.enums;


/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
public enum GenderEnum {

    /**
     * gender
     */
    FEMALE("f"),

    MALE("m"),

    OTHER("o"),
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
