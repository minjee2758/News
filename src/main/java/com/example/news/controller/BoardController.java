package com.example.news.controller;

import com.example.news.common.ApiResponse;
import com.example.news.dto.boardDto.BoardRequestDto;
import com.example.news.dto.boardDto.BoardResponseDto;
import com.example.news.entity.User;
import com.example.news.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponseDto>> createBoard(
            @RequestBody BoardRequestDto dto, HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        BoardResponseDto response = boardService.createBoard(dto.getTitle(), dto.getContent(), loginUser.getId());
        return ResponseEntity.status(201).body(ApiResponse.created(response, "게시글이 생성되었습니다"));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoard(
            @PathVariable Long boardId,
            @RequestParam Long userId,
            @RequestBody BoardRequestDto dto) {
        BoardResponseDto response = boardService.updateBoard(boardId, dto.getTitle(), dto.getContent(), userId);
        return ResponseEntity.ok(ApiResponse.success(response, "게시글이 수정되었습니다"));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(
            @PathVariable Long boardId,
            @RequestParam Long userId) {
        boardService.deleteBoard(boardId, userId);
        return ResponseEntity.noContent().build(); // 메시지 없음
    }


}
