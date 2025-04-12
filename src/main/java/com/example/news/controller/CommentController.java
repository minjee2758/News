package com.example.news.controller;

import com.example.news.common.CommonResponse;
import com.example.news.common.SuccessCode;
import com.example.news.dto.commentDto.CommentRequestDto;
import com.example.news.dto.commentDto.CommentResponseDto;
import com.example.news.entity.Comment;
import com.example.news.entity.User;
import com.example.news.service.BoardService;
import com.example.news.service.CommentService;
import com.sun.net.httpserver.Authenticator;
import jakarta.persistence.PreUpdate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    
    // 댓글 작성
    @PostMapping("/{boardId}/comments")
    public ResponseEntity<CommonResponse<CommentResponseDto>> createBoardComment(
            HttpServletRequest request,
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto dto
    ) {
        User loginUser =  (User) request.getSession().getAttribute("loginUser");
        CommentResponseDto boardComment = commentService.createBoardComment(loginUser,boardId,dto.getContent());

        return ResponseEntity.ok(CommonResponse.of(SuccessCode.CREATE_COMMENT_SUCCESS,boardComment));
    }
    
    // 댓글 전체 조회
    @GetMapping("/{boardId}/comments")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> getBoardComments(
            HttpServletRequest request,
            @PathVariable Long boardId
    ) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        List<CommentResponseDto> commentList = commentService.getBoardComments(loginUser,boardId);
        return ResponseEntity.ok(CommonResponse.of(SuccessCode.FIND_SUCCESS, commentList));

    }
    
    // 댓글 수정
    @PatchMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentResponseDto>> updateBoardComment(
            HttpServletRequest request,
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto dto
    ) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        CommentResponseDto editedComment = commentService.updateBoardComment(loginUser,boardId, commentId,dto.getContent());
        return ResponseEntity.ok(CommonResponse.of(SuccessCode.UPDATE_COMMENT_SUCCESS, editedComment));

    }

    // 댓글 삭제
    @DeleteMapping("/{boardId}/comments/{commentId}")
    public ResponseEntity<CommonResponse<CommentResponseDto>> deleteBoardComment(
            HttpServletRequest request,
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        CommentResponseDto editedComment = commentService.deleteBoardComment(loginUser,boardId, commentId);
        return ResponseEntity.ok(CommonResponse.of(SuccessCode.DELETE_SUCCESS, editedComment));

    }




}
