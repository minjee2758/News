package com.example.news.dto.friendDto;

import com.example.news.entity.Friendship;
import lombok.Getter;

@Getter
public class FriendshipRequestDto {

    private final Friendship.Status status;

    public FriendshipRequestDto(Friendship.Status status) {
        this.status = status;
    }
}
