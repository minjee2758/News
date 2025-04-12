package com.example.news.dto.boardDto;

import com.example.news.entity.Board;
import lombok.Getter;


@Getter
public class BoardResponseDto {

    private final String title;
    private final String content;
    private final int likeCount;

    public BoardResponseDto(Board board, int likeCount) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.likeCount = likeCount;
    }

    // 생성할때는 좋아요수가 0
    public BoardResponseDto(Board board) {
        this(board, 0);
    }

}

