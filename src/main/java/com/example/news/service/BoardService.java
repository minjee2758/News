package com.example.news.service;

import com.example.news.dto.boardDto.BoardResponseDto;
import org.springframework.data.domain.Page;
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

    //반환 타입이 Page<BoardResponseDto>라서 페이징 정보 전체를 포함
    //Controller에서 바로 응답으로 사용 가능
    Page<BoardResponseDto> getAllBoards(int page);
    List<FriendBoardResponseDto> getFriendPosts(Long loginUserId, Long friendId);
}
