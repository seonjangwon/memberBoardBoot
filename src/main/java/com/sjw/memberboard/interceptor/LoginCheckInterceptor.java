package com.sjw.memberboard.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        // 사용자가 요청한 주소
        String requestURI = request.getRequestURI();
        System.out.println("requestURI = " + requestURI);
        // 세션을 사져옴
        HttpSession session = request.getSession();
        // 세션에 로그인 정보가 있는지 확인
        if(session.getAttribute("loginEmail")==null) {
            // 미로그인 상태
            response.sendRedirect(("/member/login?URL="+requestURI));
            return false;
        } else {
            //로그인 상태
            return true;
        }
    }
}
