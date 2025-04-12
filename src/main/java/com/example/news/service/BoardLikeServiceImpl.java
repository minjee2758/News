package com.example.news.service;

import com.example.news.dto.boardLikeDto.BoardLikeResponse;
import com.example.news.entity.Board;
import com.example.news.entity.BoardLike;
import com.example.news.entity.User;
import com.example.news.exception.CustomException;
import com.example.news.exception.FailCode;
import com.example.news.repository.BoardLikeRepository;
import com.example.news.repository.BoardRepository;
import com.example.news.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BoardLikeServiceImpl implements BoardLikeService {
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final UserRepository userRepository;


    @Override
    public BoardLikeResponse boardLike(Long boardId, Long userId) {
        log.info("좋아요 서비스 진입");
        Board board = boardRepository.getBoardById(boardId);
        Long boardWriter = board.getUser().getId();

        if (userId.equals(boardWriter)) {
            throw new CustomException(FailCode.SELF_LIKE_NOT_ALLOWED);
        }
        if (boardLikeRepository.existsByUserIdAndBoardId(userId,boardId)) {
            throw new CustomException(FailCode.ALREADY_LIKED_POST);
        }

        User user = userRepository.findById(userId).get();

        BoardLike boardLike = new BoardLike(user, board);
        boardLikeRepository.save(boardLike);

        return new BoardLikeResponse(boardLike.getUser().getId(), boardId);
    }

    @Override
    public BoardLikeResponse boardLikeDelete(Long boardId, Long userId) {
        Board board = boardRepository.getBoardById(boardId);
        Long boardWriter = board.getUser().getId();

        if (!boardLikeRepository.existsByUserIdAndBoardId(userId,boardId)) {
            throw new CustomException(FailCode.NO_LIKE_POST);
        }
        User user = userRepository.findById(userId).get();
        BoardLike boardLike = boardLikeRepository.findByUserIdAndBoardId(user.getId(), boardId);
        boardLikeRepository.delete(boardLike);
        return new BoardLikeResponse(boardLike.getUser().getId(), boardId);
    }
}
