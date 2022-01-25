package com.sjw.memberboard.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        String requestURI =request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        HttpSession session = request.getSession();

        if (session.getAttribute("loginEmail")!="admin"){
            // 관리자 아님
            // 인덱스로 보냄
            response.sendRedirect("/");
            return false;
        } else {
            return  true;
        }
    }
}
