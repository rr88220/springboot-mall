package com.example.mall.dao;

import com.example.mall.dto.UserRegisterRequest;
import com.example.mall.model.User;

public interface UserDao {

    public Integer createUser(UserRegisterRequest userRegisterRequest);

    public User getUserById(Integer userId);

    public User getUserByEmail(String email);
}
