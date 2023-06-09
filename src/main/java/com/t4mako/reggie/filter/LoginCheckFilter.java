package com.t4mako.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.t4mako.reggie.common.BaseContext;
import com.t4mako.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author T4mako
 * @date 2023/3/4 14:00
 */
//检查用户是否已经完成登录
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*") //拦截所有请求
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //HttpServletRequest和ServletRequest都是接口，HttpServletRequest比ServletRequest多了一些针对于Http协议的方法。 例如：getHeader()， getMethod() ， getSession()
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1、获取本次请求的URL
        String requestURI = request.getRequestURI();
        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg", //front的短信验证
                "/user/login",   //用户登录
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };

        //2、判断本次请求是否需要处理
        boolean check = check(urls,requestURI);

        //3、如果不需要处理，则直接放行
        if(check){
            filterChain.doFilter(request,response);
            return;
        }

        //4-1、判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("employee") != null){
            log.info("员工已登录，用户id为：{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }

        //4-2、判断移动端登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("user") != null){

            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }

        //5、如果未登录则返回未登录结果,通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));//前端拦截器做判断，如果返回的值为NOTLOGIN，跳转到登录页面
    }

    //路径匹配，检查本次请求是否需要放行
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
