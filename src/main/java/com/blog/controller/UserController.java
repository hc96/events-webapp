package com.blog.controller;

import com.blog.pojo.Result;
import com.blog.pojo.User;
import com.blog.service.UserService;
import com.blog.utils.JwtUtil;
import com.blog.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(
            @Pattern(regexp = "^\\S{5,16}$")String username, @Pattern(regexp = "^\\S{5,16}$") String password
    ){
       User user = userService.findByUsername(username);

       if(user == null){
           userService.register(username, password);
           return Result.success();
       }else{
           return Result.error("Username has been used.");
       }

    }


    @PostMapping("/login")
    public Result login(            @Pattern(regexp = "^\\S{5,16}$")String username, @Pattern(regexp = "^\\S{5,16}$") String password
    ){
        User loginUser = userService.findByUsername(username);

        if(loginUser == null){
            return Result.error("username is invalid");
        }

        if(Md5Util.getMD5String(password).equals(loginUser.getPassword())){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            String token = JwtUtil.genToken(claims);

            return Result.success(token);
        }

        return Result.error("wrong password");

    }

}
