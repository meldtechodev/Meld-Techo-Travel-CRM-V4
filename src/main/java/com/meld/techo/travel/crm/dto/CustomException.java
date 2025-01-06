package com.meld.techo.travel.crm.dto;

public class CustomException extends RuntimeException {
    private String errorCode;
 
    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
 
    public String getErrorCode() {
        return errorCode;
    }
 
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}