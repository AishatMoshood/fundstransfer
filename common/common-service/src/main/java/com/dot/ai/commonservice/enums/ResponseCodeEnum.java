package com.dot.ai.commonservice.enums;


/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
public enum ResponseCodeEnum {

    SCS01("SCS01", StatusEnum.SUCCESSFUL,"Completed successfully."),
    SCS02("SCS02", StatusEnum.SUCCESSFUL,"All transactions returned, " +
            "since no param was provided."),
    SCS03("SCS03", StatusEnum.SUCCESSFUL,"No transactions found."),
    PEN01("PEN01", StatusEnum.PENDING,"Request in progress"),
    PEN02("PEN02", StatusEnum.PENDING,"Unable to locate record"),
    PEN03("PEN03", StatusEnum.PENDING,"System error"),
    FA01("FA01", StatusEnum.FAILED,"Invalid Sender"),
    FA07("FA07", StatusEnum.FAILED,"Decryption failed"),
    FA10("FA10", StatusEnum.FAILED,"Duplicate transaction"),
    FA11("FA11", StatusEnum.FAILED,"System Error"),
    FA15("FA15", StatusEnum.FAILED,"One or more required fields is null or empty");

    private String respCode;

    private StatusEnum status;

    private String respMsg;


    ResponseCodeEnum(String respCode, StatusEnum status, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.status = status;
    }

    public static StatusEnum getStatus(String respCode){
        for (ResponseCodeEnum value : values()) {
            if (value.getRespCode().equals(respCode)){
                return value.getStatus();
            }
        }
        return null;
    }

    public String getRespCode() {
        return respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

}
