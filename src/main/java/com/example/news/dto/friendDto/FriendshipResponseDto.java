package com.example.news.dto.friendDto;


import com.example.news.entity.Friendship;
import lombok.Getter;

@Getter
public class FriendshipResponseDto {
    private final Long requesterId;
    private final Long receiverId;
    private final Friendship.Status status;

    public FriendshipResponseDto(Long requesterId, Long receiverId, Friendship.Status status) {
        this.requesterId = requesterId;
        this.receiverId = receiverId;
        this.status = status;
    }
}
