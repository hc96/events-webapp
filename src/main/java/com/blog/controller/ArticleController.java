package com.blog.controller;

import com.blog.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/list")
    public Result list(){
        return Result.success("yes it is a list");
    }

}
