package com.sxy.community.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(ICustomErrorCode customizeErrorCode) {
        this.message=customizeErrorCode.getMessage();
        this.code=customizeErrorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
