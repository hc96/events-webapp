package com.blog.service;

import com.blog.pojo.Article;
import com.blog.pojo.PageBean;

public interface ArticleService {

    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
}
