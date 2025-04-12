package com.example.news.repository;


import com.example.news.entity.Board;
import com.example.news.entity.Friendship;
import com.example.news.entity.User;
import com.example.news.exception.CustomException;
import com.example.news.exception.FailCode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    // FriendshipRepository에 친구 관계(보류 포함)가 존재하는지 확인
    boolean existsByRequesterAndReceiver(User loginUser, User receiver);

    // 요청자-수신자 순서와 상관없이 친구 관계 조회
    Optional<Friendship> findByRequesterAndReceiver(User loginUser, User receiver);

    default Friendship findByRequesterAndReceiverOrElseThrow(User requester, User loginUser) {
        return findByRequesterAndReceiver(requester, loginUser).orElseThrow(() -> new CustomException(FailCode.USER_NOT_FOUND));
    }

    List<Friendship> findAllByReceiverAndStatus(User loginUser, Friendship.Status status , Sort sort);

    List<Friendship> findAllByRequesterAndStatus(User loginUser, Friendship.Status status, Sort sort);
}