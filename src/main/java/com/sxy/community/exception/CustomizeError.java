package com.sxy.community.exception;

public class CustomizeError extends RuntimeException{
    private String message;
    Integer code;

    public CustomizeError(ICustomErrorCode customizeErrorCode) {
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
