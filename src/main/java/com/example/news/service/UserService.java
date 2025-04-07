package com.example.news.service;

import com.example.news.dto.userDto.UserResponseDto;

public interface UserService {

    UserResponseDto signUp(String username, String email, String mbti, String password);
}
