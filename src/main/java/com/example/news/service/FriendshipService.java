package com.example.news.service;


import com.example.news.dto.friendDto.FriendshipResponseDto;
import com.example.news.entity.Friendship;
import com.example.news.entity.User;

public interface FriendshipService {
    FriendshipResponseDto sendFriendRequest(User loginUser, Long receiverId, Friendship.Status status);

    FriendshipResponseDto handleFriendRequest(User loginUser, Long requesterId, Friendship.Status status);

    void removeFriend(User loginUser, Long requesterId);
}
