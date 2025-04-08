package com.example.news.dto.userDto;

import lombok.Getter;

@Getter
public class UpdatePwResponseDto {
    private final String name;
    private final String password;
    private final String newPassword;

    public UpdatePwResponseDto(String name, String password, String newPassword) {
        this.name = name;
        this.password = password;
        this.newPassword = newPassword;
    }
}
