package com.example.news.service;


import com.example.news.dto.friendDto.FriendshipResponseDto;
import com.example.news.entity.Friendship;
import com.example.news.entity.Friendship.Status;
import com.example.news.entity.User;
import com.example.news.repository.FriendshipRepository;
import com.example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class FriendshipServiceImpl implements FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    // post 요청 처리
    public FriendshipResponseDto sendFriendRequest(User loginUser, Long receiverId, Status status) {

        if (loginUser.getId().equals(receiverId)) {
            throw new IllegalArgumentException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
        }

        // id를 통해 수신자 가져오기
        User receiver = userRepository.findUserByIdOrElseThrow(receiverId);

        // 이전에 같은 요청이 있었는지 확인
        // 어느쪽에서 친구 요청을 보냈든, 이미 레포지토리에 Friendship 관계가 존재할 경우 예외 처리
        boolean exists = friendshipRepository.existsByRequesterAndReceiver(loginUser, receiver) || friendshipRepository.existsByRequesterAndReceiver(receiver, loginUser);

        if (exists) {
            throw new IllegalStateException("이미 친구 요청을 보냈습니다.");
        }

        Friendship friendship = new Friendship(loginUser, receiver, status);
        friendshipRepository.save(friendship);

        return new FriendshipResponseDto(loginUser.getId(), receiver.getId(), status);

    }

    // patch 요청 처리
    public FriendshipResponseDto handleFriendRequest(User loginUser, Long requesterId, Status status) {

        // 유효한 상태값인지 확인
        if (status != Status.ACCEPTED && status != Status.REJECTED) {
            throw new IllegalArgumentException("올바르지 않은 요청 상태입니다.");
        }

        // 로그인한 사용자가 null이면 예외
        if (loginUser == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 친구 요청을 한 사람 불러오기
        User requester = userRepository.findUserByIdOrElseThrow(requesterId);

        // 자기 자신에게 친구 수락/거절할 경우 예외 처리
        if (loginUser.getId().equals(requester.getId())) {
            throw new IllegalArgumentException("자기 자신에게 친구 수락/거절을 할 수 없습니다.");
        }

        // 기존 친구 요청 가져오기
        Friendship friendship = friendshipRepository
                .findByRequesterAndReceiver(requester, loginUser)
                .orElseThrow(() -> new IllegalStateException("친구 요청이 존재하지 않습니다."));

        if (status == Status.REJECTED) {
            friendshipRepository.delete(friendship);
            return new FriendshipResponseDto(requesterId, loginUser.getId(), status);
        }

        // 상태 업데이트
        friendship.setStatus(status);

        // 기존 리포지토리에 저장
        friendshipRepository.save(friendship);

        return new FriendshipResponseDto(requesterId, loginUser.getId(), status);
    }

    public void removeFriend(User loginUser, Long requesterId) {
        // 친구 요청자를 id로 조회
        User requester = userRepository.findUserByIdOrElseThrow(requesterId);

        // 로그인한 사용자(Receiver) id와 요청자 id값을 통해 friendship 가져오기
        Friendship friendship = friendshipRepository.findByRequesterAndReceiverOrElseThrow(requester, loginUser);

        // friendshipRepository에서 해당 friendship 삭제
        friendshipRepository.delete(friendship);
    }
}
