package com.example.news.repository;

import com.example.news.common.CustomException;
import com.example.news.common.Error;
import com.example.news.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    default User findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email)
                .orElseThrow(() ->  new CustomException(Error.USER_NOT_FOUND));
    }

    Optional<User> findUserById(Long id);

    default User findUserByIdOrElseThrow(Long id) {
        return findUserById(id)
                .orElseThrow(()->new CustomException(Error.USER_NOT_FOUND));
    }
}
