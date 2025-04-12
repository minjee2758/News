package com.example.news.service;

import com.example.news.dto.boardLikeDto.BoardLikeResponse;
import org.springframework.stereotype.Service;

@Service
public interface BoardLikeService {
    BoardLikeResponse boardLike(Long boardId, Long userId);

    BoardLikeResponse boardLikeDelete(Long boardId, Long id);
}
