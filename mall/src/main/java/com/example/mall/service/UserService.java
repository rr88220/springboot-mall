package com.example.mall.service;

import com.example.mall.dto.UserRegisterRequest;
import com.example.mall.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
