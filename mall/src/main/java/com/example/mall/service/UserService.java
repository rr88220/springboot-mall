package com.example.mall.service;

import com.example.mall.dto.UserLoginRequest;
import com.example.mall.dto.UserRegisterRequest;
import com.example.mall.model.User;

public interface UserService {
    public Integer register(UserRegisterRequest userRegisterRequest);

    public User getUserById(Integer userId);

    public User login(UserLoginRequest userLoginRequest);
}
