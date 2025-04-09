package com.example.news.service;

import com.example.news.dto.boardDto.BoardResponseDto;
import com.example.news.entity.Board;
import com.example.news.entity.User;
import com.example.news.repository.BoardRepository;
import com.example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BoardResponseDto createBoard(String title, String content, Long userId) {
        User user = getUser(userId);
        Board board = Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
        return new BoardResponseDto(boardRepository.save(board));
    }

    @Override
    @Transactional
    public BoardResponseDto updateBoard(Long boardId, String title, String content, Long userId) {
        Board board = getBoard(boardId);
        if (!board.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        board.update(title, content);
        return new BoardResponseDto(board);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = getBoard(boardId);
        if (!board.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        boardRepository.delete(board);
    }

    @Override
    public List<BoardResponseDto> getBoardsByUser(Long userId) {
        User user = getUser(userId);
        return boardRepository.findAllByUser(user).stream()
                .map(BoardResponseDto::new)
                .toList();
    }

    @Override
    public BoardResponseDto getBoardByUser(Long userId, Long boardId) {
        Board board = getBoard(boardId);
        if (!board.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 유저의 게시글이 아닙니다.");
        }
        return new BoardResponseDto(board);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
    }

    private Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
    }
}
