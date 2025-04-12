package com.example.news.dto.boardLikeDto;

import lombok.Getter;

@Getter
public class BoardLikeReqestDto {
    private final Long boardId;

    public BoardLikeReqestDto(Long boardId) {
        this.boardId = boardId;
    }
}
