package com.sjw.memberboard.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/member/save","/member/login","/member/logout","/member/emailch","/css/**");

        registry.addInterceptor(new AdminCheckInterceptor())
                .order(2)
                .addPathPatterns("/member/admin","/member/","/member/delete/*");
    }



}
