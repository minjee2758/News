package com.example.news.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private final String email;
    private final String mbti;
    private final String password;
    private final String username;
    private final Long id;

    public UserRequestDto(String email, String password, String username, String mbti, Long id) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.mbti = mbti;
        this.id = id;
    }
}
