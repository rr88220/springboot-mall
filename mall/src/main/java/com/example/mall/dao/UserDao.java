package com.example.mall.dao;

import com.example.mall.dto.UserRegisterRequest;
import com.example.mall.model.User;

public interface UserDao {

    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
