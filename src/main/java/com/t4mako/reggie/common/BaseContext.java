package com.t4mako.reggie.common;

/**
 * @author T4mako
 * @date 2023/3/6 14:18
 */
//基于ThreadLocal封装工具类，用于保存和获取当前登录用户的id
public class BaseContext{
    private static ThreadLocal<Long> threadLocal = new InheritableThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
