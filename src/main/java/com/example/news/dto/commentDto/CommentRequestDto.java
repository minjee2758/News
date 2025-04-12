package com.example.news.dto.commentDto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private final String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
