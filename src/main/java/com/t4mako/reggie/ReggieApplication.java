package com.t4mako.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author T4mako
 * @date 2023/2/26 15:11
 */
@Slf4j
@SpringBootApplication //这是一个springboot应用
@ServletComponentScan("com.t4mako.reggie") //指定原生Servlet组件都放在那里，自动通过注解扫描注册
@EnableTransactionManagement //开启事务支持
@EnableCaching //开启Spring Cache注解方式缓存
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
    }
}
