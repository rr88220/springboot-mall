package com.example.mall.service.impl;

import com.example.mall.dao.UserDao;
import com.example.mall.dto.UserRegisterRequest;
import com.example.mall.model.User;
import com.example.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        Integer userId = userDao.createUser(userRegisterRequest);
        return userId;
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
