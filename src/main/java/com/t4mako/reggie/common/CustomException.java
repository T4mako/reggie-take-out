package com.t4mako.reggie.common;

/**
 * @author T4mako
 * @date 2023/3/6 16:14
 */
//自定义业务异常类
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}
