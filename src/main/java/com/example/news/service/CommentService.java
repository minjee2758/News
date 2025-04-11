package com.example.news.service;

import com.example.news.dto.commentDto.CommentResponseDto;
import com.example.news.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    CommentResponseDto createBoardComment(User loginUser, Long boardId, String content);

    List<CommentResponseDto> getBoardComments(User loginUser, Long boardId);

    CommentResponseDto updateBoardComment(User loginUser, Long boardId, Long commentId,String newContent);

    CommentResponseDto deleteBoardComment(User loginUser, Long boardId, Long commentId);

}
