package com.blog.controller;

import com.blog.pojo.Result;
import com.blog.pojo.User;
import com.blog.service.UserService;
import com.blog.utils.JwtUtil;
import com.blog.utils.Md5Util;
import com.blog.utils.ThreadLocalUtil;
import org.springframework.util.StringUtils;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User user = userService.findByUsername(username);

        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);

        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String url){
        userService.updateAvatar(url);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("missing hahahah");
        }

       Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String)claims.get("username");
        User user = userService.findByUsername(username);

        if(!user.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("wrong password");
        }

        if(!newPwd.equals(rePwd)){
            return Result.error("passwords do not match");
        }

        userService.updatePwd(newPwd);
        return Result.success();
    }

}
