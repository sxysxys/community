package com.sxy.community.exception;

public enum  CustomizeErrorCode implements ICustomErrorCode{
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！！！"),
    QUESTION_NOT_FOUND(2001, "你找到问题不在了，要不要换个试试？"),
    TARGET_NOT_FOUND(2000,"未选中任何问题进行评论或回复"),
    NO_LOGIN(1600,"没登陆哦亲"),
    SUCCESS_RETURN(200,"登录成功"),
    TYPE_PARAM_NOTEXIST(1620,"类型值不存在"), PARENT_NOT_EXIST(1630,"没有找到这个问题的源头" ),
    NO_PEOPLE_EXIST(1640, "没找到这个人"), NO_CONTENT(1660,"请评论后再发布!" );
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
