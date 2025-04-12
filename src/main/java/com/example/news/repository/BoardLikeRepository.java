package com.example.news.repository;


import com.example.news.entity.BoardLike;
import com.example.news.exception.CustomException;
import com.example.news.exception.FailCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    boolean existsByUserIdAndBoardId(Long userId, Long boardId);

    BoardLike findByUserIdAndBoardId(Long id, Long boardId);
}
