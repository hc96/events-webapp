package com.blog.controller;

import com.blog.pojo.Result;
import com.blog.pojo.User;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(
            String username, String password
    ){
       User user = userService.findByUsername(username);

       if(user == null){
           userService.register(username, password);
           return Result.success();
       }else{
           return Result.error("Username has been used.");
       }

    }


}
