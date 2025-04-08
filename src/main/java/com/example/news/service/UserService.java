package com.example.news.service;

import com.example.news.dto.userDto.UserResponseDto;
import com.example.news.entity.User;

public interface UserService {

    UserResponseDto signUp(String username, String email, String mbti, String password);

    User login(String email, String password);

    void logout(String password);
}
