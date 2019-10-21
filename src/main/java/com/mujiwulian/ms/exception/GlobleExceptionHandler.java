package com.mujiwulian.ms.exception;

import com.mujiwulian.ms.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.BindException;

@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(HttpServletRequest request, Exception e){
        if (e instanceof BindException){
            BindException bindException= (BindException) e;
            return "绑定参数异常";
        }else {
            return "异常信息:"+e.getMessage();
        }
    }
}