package com.blog.interceptors;


import com.blog.pojo.Result;
import com.blog.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("Authorization");

        try{
            Map<String, Object> claims = JwtUtil.parseToken(token);
            return true;

        }catch(Exception e){
            response.setStatus(401);
            return false;
        }
    }
}
