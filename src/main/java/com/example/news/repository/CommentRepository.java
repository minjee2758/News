package com.example.news.repository;

import com.example.news.entity.Board;
import com.example.news.entity.Comment;
import com.example.news.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBoard(Board board);

    Comment findAllByBoardAndUserAndId(Board board, User loginUser,Long Id);
}
