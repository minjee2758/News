package com.example.news.controller;

import com.example.news.common.ApiResponse;
import com.example.news.dto.boardDto.BoardResponseDto;
import com.example.news.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/boards")
@RequiredArgsConstructor
public class UserBoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardResponseDto>>> getBoardsByUser(@PathVariable Long userId) {
        List<BoardResponseDto> response = boardService.getBoardsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(response, "사용자의 모든 게시글을 조회했습니다"));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> getBoardByUser(
            @PathVariable Long userId,
            @PathVariable Long boardId) {
        BoardResponseDto response = boardService.getBoardByUser(userId, boardId);
        return ResponseEntity.ok(ApiResponse.success(response, "게시글을 조회했습니다"));
    }



}
