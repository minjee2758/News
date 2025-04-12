package com.example.news.controller;

import com.example.news.common.CommonResponse;
import com.example.news.common.SuccessCode;
import com.example.news.dto.boardLikeDto.BoardLikeReqestDto;
import com.example.news.dto.boardLikeDto.BoardLikeResponse;
import com.example.news.entity.User;
import com.example.news.service.BoardLikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board/like")
@RequiredArgsConstructor
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/{boardId}")
    public ResponseEntity<CommonResponse<BoardLikeResponse>> postLike(
            @PathVariable Long boardId,
            HttpServletRequest request) {

        User loginUser = (User) request.getSession().getAttribute("loginUser");
        log.info("로그인 유저 찾음");

        BoardLikeResponse boardLikeResponse = boardLikeService.boardLike(boardId, loginUser.getId());

        return ResponseEntity.status(SuccessCode.LIKE_SUCCESS.getHttpStatus())
                .body(CommonResponse.of(SuccessCode.LIKE_SUCCESS, boardLikeResponse));

    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<CommonResponse<BoardLikeResponse>> deleteBoardLike
            (@PathVariable Long boardId, HttpServletRequest request) {

        User loginUser = (User) request.getSession().getAttribute("loginUser");
        BoardLikeResponse boardLikeResponse = boardLikeService.boardLikeDelete(boardId, loginUser.getId());

        return new ResponseEntity<>(CommonResponse.of(SuccessCode.LIKE_DELETE_SUCCESS, boardLikeResponse), HttpStatus.OK);
    }
}
