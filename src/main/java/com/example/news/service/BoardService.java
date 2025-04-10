package com.example.news.service;

import com.example.news.dto.boardDto.BoardResponseDto;
import com.example.news.dto.friendDto.FriendBoardResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    BoardResponseDto createBoard(String title, String content, Long userId);

    BoardResponseDto updateBoard(Long boardId, String title, String content, Long userId);

    void deleteBoard(Long boardId, Long userId);

    List<BoardResponseDto> getBoardsByUser(Long userId);

    BoardResponseDto getBoardByUser(Long userId, Long boardId);

    List<FriendBoardResponseDto> getFriendPosts(Long loginUserId, Long friendId);
}
