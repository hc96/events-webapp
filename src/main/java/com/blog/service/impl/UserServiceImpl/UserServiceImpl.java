package com.blog.service.impl.UserServiceImpl;

import com.blog.mapper.UserMapper;
import com.blog.pojo.User;
import com.blog.service.UserService;
import com.blog.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username){
        User user = userMapper.findByUsername(username);
        return user;
    }

    @Override
    public void register(String username, String password){
        String md5Str = Md5Util.getMD5String(password);
        userMapper.add(username, md5Str);
    }

    @Override
    public void update(User user){
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }
}
