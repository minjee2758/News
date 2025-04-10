package com.example.news.service;

import com.example.news.dto.userDto.UserResponseDto;
import com.example.news.entity.User;

public interface UserService {

    UserResponseDto signUp(String username, String email, String mbti, String password);

//    UserResponseDto login(String email, String password);

    UserResponseDto logout(String email, String password);

    boolean updatePw(String email, String password, String newPassword);

    UserResponseDto findUserById(Long id);

    void withdraw(String email, String password);

    User findUserByEmail(String email);
}
