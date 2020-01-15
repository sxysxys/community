package com.sxy.community.DTO;

import com.sxy.community.exception.CustomizeErrorCode;
import com.sxy.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDto {
    private Integer code;
    private String message;

    public ResultDto(Integer code,String message){
        this.code=code;
        this.message=message;
    }

    public static ResultDto errorOf(Integer code,String message){
        return new ResultDto(code,message);
    }

    public static ResultDto errorOf(CustomizeErrorCode noLogin) {
        return new ResultDto(noLogin.getCode(),noLogin.getMessage());
    }

    public static ResultDto success(CustomizeErrorCode successReturn) {
        return new ResultDto(successReturn.getCode(),successReturn.getMessage());
    }

    public static ResultDto errorOf(CustomizeException ex) {
        return new ResultDto(ex.getCode(),ex.getMessage());
    }
}
