package com.blog.service;

import com.blog.pojo.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User findByUsername(String username);

    void register(String username, String password);

    void update(User user);

    void updateAvatar(String url);

    void updatePwd(String pwd);
}
