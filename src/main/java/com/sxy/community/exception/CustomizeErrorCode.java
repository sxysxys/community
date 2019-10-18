package com.sxy.community.exception;

public enum  CustomizeErrorCode implements ICustomErrorCode{
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！！！"),
    QUESTION_NOT_FOUND(2001, "你找到问题不在了，要不要换个试试？");
    String message;
    Integer code;
    CustomizeErrorCode(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
