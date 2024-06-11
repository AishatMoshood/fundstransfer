package com.dot.ai.commonservice.enums;


/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
public enum ResponseCodeEnum {

    SCS01("SCS01", TransactionStatusEnum.SUCCESSFUL,"Completed successfully."),
    SCS02("SCS02", TransactionStatusEnum.SUCCESSFUL,"All transactions returned, " +
            "since no param was provided."),
    SCS03("SCS03", TransactionStatusEnum.SUCCESSFUL,"No transactions found."),
    P01("P01",TransactionStatusEnum.PENDING,"Request in progress"),
    P02("P02",TransactionStatusEnum.PENDING,"Unable to locate record"),
    P03("P03",TransactionStatusEnum.PENDING,"System error"),
    F01("F01",TransactionStatusEnum.FAILED,"Invalid Sender"),
    F02("F02",TransactionStatusEnum.FAILED,"Unknown Bank Code"),
    F03("F03",TransactionStatusEnum.FAILED,"Invalid Account"),
    F04("F04",TransactionStatusEnum.FAILED,"Invalid transaction"),
    F05("F05",TransactionStatusEnum.FAILED,"Invalid Amount"),
    F07("F07",TransactionStatusEnum.FAILED,"Decryption failed"),
    F08("F08",TransactionStatusEnum.FAILED,"Transaction not permitted to sender"),
    F09("F09",TransactionStatusEnum.FAILED,"Transfer limit Exceeded"),
    F10("F10",TransactionStatusEnum.FAILED,"Duplicate transaction"),
    F11("F11",TransactionStatusEnum.FAILED,"System Error"),
    F12("F12",TransactionStatusEnum.FAILED,"Insufficient Fund"),
    F13("F13",TransactionStatusEnum.FAILED,"Invalid Authorization Key"),
    F14("F14",TransactionStatusEnum.FAILED,"Account Verification Failed"),
    F15("F15",TransactionStatusEnum.FAILED,"One or more required fields is null or empty"),
    F16("F16",TransactionStatusEnum.FAILED,"Invalid beneficiary"),

    private String respCode;

    private TransactionStatusEnum status;

    private String respMsg;


    ResponseCodeEnum(String respCode, TransactionStatusEnum status, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.status = status;
    }

    public static ResponseCodeEnum getRespCode(String respCode){
        for (ResponseCodeEnum value : values()) {
            if (value.getRespCode().equals(respCode)){
                return value;
            }
        }
        return null;
    }

    public static TransactionStatusEnum getStatus(String respCode){
        for (ResponseCodeEnum value : values()) {
            if (value.getRespCode().equals(respCode)){
                return value.getStatus();
            }
        }
        return null;
    }


    public static boolean isSuccessful(String respCode){
        if (ResponseCodeEnum.SCS01.getRespCode().equals(respCode)){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isPending(String respCode){
        if (ResponseCodeEnum.P01.getRespCode().equals(respCode)
        || ResponseCodeEnum.P02.getRespCode().equals(respCode)
        || ResponseCodeEnum.P03.getRespCode().equals(respCode)){
            return true;
        }else {
            return false;
        }
    }


    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public TransactionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TransactionStatusEnum status) {
        this.status = status;
    }

}
