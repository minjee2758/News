package com.example.news.repository;

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
                .orElseThrow(() -> new NoSuchElementException("해당 이메일의 사용자를 찾을 수 없습니다: " + email));
    }

    Optional<User> findUserById(Long id);

    default User findUserByIdOrElseThrow(Long id) {
        return findUserById(id)
                .orElseThrow(()->new NoSuchElementException("id" + id + "의 사용자를 찾을 수 없습니다"));
    }
}
