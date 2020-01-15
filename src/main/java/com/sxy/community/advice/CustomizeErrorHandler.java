package com.sxy.community.advice;

import com.alibaba.fastjson.JSON;
import com.sxy.community.DTO.ResultDto;
import com.sxy.community.exception.CustomizeErrorCode;
import com.sxy.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeErrorHandler {
    /**
     * 错误处理，传进来的如果是json则返回json，如果是表单提交则返回静态页面
     * 但是没有办法在一个方法中既返回静态页面又返回json数据，只有通过servlet的response给它硬写回去
     * @param request
     * @param ex
     * @param model
     * @return
     */
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response) {
        String contentType = request.getContentType();
        if (contentType.equals("application/json")){
            ResultDto resultDto;
            if(ex instanceof CustomizeException){
                resultDto = ResultDto.errorOf((CustomizeException) ex);
            }else {
                resultDto = ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR.getCode(),
                        CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            if(ex instanceof CustomizeException){
                model.addAttribute("message",ex.getMessage());
                model.addAttribute("code",((CustomizeException) ex).getCode());
            }
            else{
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
