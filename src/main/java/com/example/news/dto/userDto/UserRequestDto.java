package com.example.news.dto.userDto;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private final String username;
    private final String email;
    private final String mbti;
    private final String password;

    public UserRequestDto(String email, String password, String username, String mbti) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.mbti = mbti;
    }
}
