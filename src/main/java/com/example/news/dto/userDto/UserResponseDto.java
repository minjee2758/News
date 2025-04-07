package com.example.news.dto.userDto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String email;
    private final String username;
    private final String mbti;

    public UserResponseDto(String email, String username, String mbti) {
        this.email = email;
        this.username = username;
        this.mbti = mbti;
    }
}
