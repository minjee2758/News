package com.example.news.dto.boardLikeDto;

import lombok.Getter;

@Getter
public class BoardLikeResponse {
    private final Long userId;
    private final Long boardId;

    public BoardLikeResponse(Long userId, Long boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }
}
