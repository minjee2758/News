package com.example.news.dto.boardLikeDto;

import lombok.Getter;

@Getter
public class BoardLikeReqestDto {
    private final Long postId;

    public BoardLikeReqestDto(Long postId) {
        this.postId = postId;
    }
}
