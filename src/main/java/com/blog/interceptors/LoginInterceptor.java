package com.blog.interceptors;


import com.blog.pojo.Result;
import com.blog.utils.JwtUtil;
import com.blog.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("Authorization");

        try{
            // get the token from redis
            ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
            String redisToken = valueOperations.get(token);

            if(redisToken == null){
                throw new RuntimeException();
            }

            Map<String, Object> claims = JwtUtil.parseToken(token);

            ThreadLocalUtil.set(claims);
            return true;

        }catch(Exception e){
            response.setStatus(401);
            return false;
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e){
        ThreadLocalUtil.remove();
    }
}
