package com.sxy.community.DTO;

import com.sxy.community.exception.CustomizeErrorCode;
import com.sxy.community.exception.CustomizeException;
import lombok.Data;

/**
 * 返回给前端的数据格式，由于不知道返回的值的类型，所以用泛型
 * @param
 */
@Data
public class ResultDto {
    private Integer code;
    private String message;
    private Object data;

    public ResultDto(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    public ResultDto(){

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

    public static  ResultDto success(Object t){  //静态泛型需要加<T>,相当于将T当作object来用，只不过springmvc能够解析出真实的数据。
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return resultDto;
    }
}
