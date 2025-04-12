package com.example.news.repository;

import com.example.news.entity.Board;
import com.example.news.entity.Comment;
import com.example.news.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByBoard(Board board);

    Comment findAllByBoardAndUserAndId(Board board, User loginUser,Long Id);
}
