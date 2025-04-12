package com.example.news.dto.friendDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FriendBoardResponseDto {

        private Long id;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private int likeCount;

        public FriendBoardResponseDto(Long id, String title, String content, LocalDateTime createdAt, int likeCount) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.createdAt = createdAt;
            this.likeCount = likeCount;
        }
}
