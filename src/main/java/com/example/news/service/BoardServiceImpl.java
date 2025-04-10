package com.example.news.service;

import com.example.news.dto.boardDto.BoardResponseDto;
import com.example.news.entity.Board;
import com.example.news.entity.User;
import com.example.news.repository.BoardRepository;
import com.example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<BoardResponseDto> getAllBoards(int userPage) {
        // 사용자 요청 page가 1부터 시작한다고 가정하고 내부 로직은 0부터 시작하게 조정
        int page = (userPage < 1) ? 0 : userPage - 1;

        // PageRequest.of()를 사용하여 페이징 조건 생성
        Pageable pageable = PageRequest.of(
                page, // 0부터 시작하는 페이지 번호
                10, // 페이지 크기: 게시글 10개씩
                Sort.by(Sort.Direction.DESC, "createdAt")
                // 생성일 기준 내림차순 정렬 (최신순)
        );

        // Repository 에서 페이징된 게시글을 가져오고 Dto로 변환
        return boardRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(BoardResponseDto::new);
    }
    //PageRequest.of(page, size, sort)로 페이징 설정
    //map(BoardResponseDto::new)로 엔티티를 DTO로 변환
    //Page 타입 그대로 리턴하면 Controller에서 전체 페이징 정보 응답 가능
}
