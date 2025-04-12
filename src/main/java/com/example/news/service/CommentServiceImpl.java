package com.example.news.service;

import com.example.news.common.CustomException;
import com.example.news.common.Error;
import com.example.news.dto.commentDto.CommentRequestDto;
import com.example.news.dto.commentDto.CommentResponseDto;
import com.example.news.entity.Board;
import com.example.news.entity.Comment;
import com.example.news.entity.User;
import com.example.news.repository.BoardRepository;
import com.example.news.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRespository;
    private final BoardRepository boardRepository;

    @Override
    public CommentResponseDto createBoardComment(User loginUser, Long boardId, String content) {
        if (loginUser == null ) {
            throw new CustomException(Error.LOGIN_REQUIRED);
        }
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));

        // content가 null이거나 공백만 있는 경우
        if (content == null || content.trim().isEmpty()) {
            throw new CustomException(Error.RESOURCE_NOT_FOUND);
        }

        Comment comment = new Comment(content, board, loginUser);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    @Override
    public List<CommentResponseDto> getBoardComments(User loginUser, Long boardId) {
        Board board = boardRespository.getBoardById(boardId);
        List<Comment> commentList = commentRepository.findAllByBoard(board);

        return commentList.stream()
                .map(CommentResponseDto::new) // 생성자 변환
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto updateBoardComment(User loginUser, Long boardId, Long commentId, String newContent) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));

        // 2. 댓글 조회
        Comment comment =commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(Error.COMMENT_NOT_FOUND));

        // 3. 댓글이 해당 게시글에 속해 있는지 확인
        if (!comment.getBoard().getId().equals(board.getId())) {
            throw new CustomException(Error.COMMENT_NOT_IN_BOARD);
        }

        // 4. 수정 권한 확인: 댓글 작성자거나, 게시글 작성자면 댓글 내용 수정
        boolean isCommentOwner = comment.getUser().equals(loginUser);
        boolean isBoardOwner = board.getUser().equals(loginUser);

        if (!(isCommentOwner || isBoardOwner)) {
            throw new CustomException(Error.ONLY_AUTHOR_CAN_MODIFY);
        }

        // 5. 댓글 내용 수정
        comment.setComment(newContent);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public CommentResponseDto deleteBoardComment(User loginUser, Long boardId, Long commentId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(Error.POST_NOT_FOUND));

        // 2. 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(Error.COMMENT_NOT_FOUND));

        // 3. 댓글이 해당 게시글에 속해 있는지 확인
        if (!comment.getBoard().getId().equals(board.getId())) {
            throw new CustomException(Error.COMMENT_NOT_IN_BOARD);
        }

        // 4. 수정 권한 확인: 댓글 작성자거나, 게시글 작성자면 댓글 내용 삭제 가능
        boolean isCommentOwner = comment.getUser().equals(loginUser);
        boolean isBoardOwner = board.getUser().equals(loginUser);

        if (!(isCommentOwner || isBoardOwner)) {
            throw new CustomException(Error.ONLY_AUTHOR_CAN_DELETE);
        }

        // 5. 댓글 삭제
        commentRepository.delete(comment);
        return new CommentResponseDto(comment);

    }
}
