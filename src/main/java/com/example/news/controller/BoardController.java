package com.example.news.controller;

import com.example.news.common.ApiResponse;
import com.example.news.common.CommonResponse;
import com.example.news.common.SuccessCode;
import com.example.news.dto.boardDto.BoardRequestDto;
import com.example.news.dto.boardDto.BoardResponseDto;
import com.example.news.entity.User;
import com.example.news.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<CommonResponse<Void>> deleteBoard(
            @PathVariable Long boardId,
            @RequestParam Long userId) {
        boardService.deleteBoard(boardId, userId);
        return ResponseEntity.ok(CommonResponse.of(SuccessCode.DELETE_SUCCESS, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<BoardResponseDto>>> getAllBoards(
            @RequestParam(defaultValue = "1") int page) { // 사용자 입장에서 1페이지가 시작
        // BoardServiceImpl 에서 페이지 번호를 내부 로직에 맞게 보정하여 전체 게시글 조회
        Page<BoardResponseDto> response = boardService.getAllBoards(page);
        // 공통 응답 형식으로 감싸서 반환
        return ResponseEntity.ok(ApiResponse.success(response, "전체 게시글을 조회했습니다"));
    }

}
