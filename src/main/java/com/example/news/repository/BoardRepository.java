package com.example.news.repository;

import com.example.news.entity.Board;
import com.example.news.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUser(User user);

    // 친구의 게시물을 최신순으로 조회
    List<Board> findAllByUserOrderByCreatedAtDesc(User user, Sort sort);
    // createdAt 기준으로 내림차순 정렬 + Pageable 적용
    Page<Board> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Board findByUserAndId(User loginUser, Long boardId);

    Board getBoardById(Long boardId);

    Board getBoardByIdAndUser(Long boardId, User loginUser);
}
