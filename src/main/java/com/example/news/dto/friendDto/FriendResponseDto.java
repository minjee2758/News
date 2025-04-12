package com.example.news.dto.friendDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendResponseDto {
    private final Long id;
    private final String username;

    public FriendResponseDto(Long id, String username, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
    }
}
