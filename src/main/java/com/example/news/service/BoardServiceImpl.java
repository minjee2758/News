package com.example.news.service;

import com.example.news.common.CustomException;
import com.example.news.common.Error;
import com.example.news.dto.boardDto.BoardResponseDto;
import com.example.news.dto.friendDto.FriendBoardResponseDto;
import com.example.news.entity.Board;
import com.example.news.entity.Friendship;
import com.example.news.entity.User;
import com.example.news.repository.BoardRepository;
import com.example.news.repository.FriendshipRepository;
import com.example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.news.entity.Friendship.Status;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.news.entity.Friendship.Status.ACCEPTED;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

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
            throw new CustomException(Error.ONLY_AUTHOR_CAN_MODIFY);
        }
        board.update(title, content);
        return new BoardResponseDto(board);
    }

    @Override
    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = getBoard(boardId);
        if (!board.getUser().getId().equals(userId)) {
            throw new CustomException(Error.ONLY_AUTHOR_CAN_DELETE);
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
            throw new CustomException(Error.NOT_USER_POST);
        }
        return new BoardResponseDto(board);
    }

    @Override
    public List<FriendBoardResponseDto> getFriendPosts(Long loginUserId, Long friendId) {
        User loginUser = userRepository.findUserByIdOrElseThrow(loginUserId);
        User friend = userRepository.findUserByIdOrElseThrow(friendId);

        // 양방향 친구 관계 확인
        Optional<Friendship> friendship1 = friendshipRepository.findByRequesterAndReceiver(loginUser, friend);
        Optional<Friendship> friendship2 = friendshipRepository.findByRequesterAndReceiver(friend, loginUser);

        // 친구 관계가 없거나 수락되지 않은 친구 관계라면 예외 처리
        if (friendship1.isEmpty() && friendship2.isEmpty()) {
            throw new CustomException(Error.FRIENDSHIP_NOT_ACCEPTED);
        }

        Friendship friendship = friendship1.orElseGet(() -> friendship2.orElseThrow(() -> new IllegalStateException("친구 관계가 아닙니다.")));

        if (friendship.getStatus() != Friendship.Status.ACCEPTED) {
            throw new CustomException(Error.FRIENDSHIP_NOT_ACCEPTED);
        }

        // 친구 게시물 가져오기 (최신순으로)
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        List<Board> friendBoardList = boardRepository.findAllByUserOrderByCreatedAtDesc(friend, sort);

        // FriendBoardResponseDto로 변환하여 반환
        return friendBoardList.stream()
                .map(board -> new FriendBoardResponseDto(
                        board.getId(),
                        board.getTitle(),
                        board.getContent(),
                        board.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(Error.USER_NOT_FOUND));
    }

    private Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));
    }
}
