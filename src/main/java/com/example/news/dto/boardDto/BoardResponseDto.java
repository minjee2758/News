package com.example.news.dto.boardDto;

import com.example.news.entity.Board;
import lombok.Getter;


@Getter
public class BoardResponseDto {

    private final String title;
    private final String content;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}

