package com.ctgu.swzl.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*登录拦截器*/
public class LoginHandleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = (String) request.getSession().getAttribute("loginUser");
        if (username==null){
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
        return true;
    }
}
