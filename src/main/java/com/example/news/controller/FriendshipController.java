package com.example.news.controller;

import com.example.news.dto.friendDto.FriendshipRequestDto;
import com.example.news.dto.friendDto.FriendshipResponseDto;
import com.example.news.entity.User;
import com.example.news.service.FriendshipServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friendship")
public class FriendshipController {

    private final FriendshipServiceImpl friendshipService;

    // 친구 추가 요청 (누구에게 요청할 것인지)
    @PostMapping("/{receiverId}")
    public ResponseEntity<FriendshipResponseDto> handleSendFriendRequest
    (
            @PathVariable Long receiverId,
            HttpServletRequest request
    ) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        // 로그인 정보가 null일 경우 예외 처리 필요

        FriendshipResponseDto friendshipResponseDto = friendshipService.sendFriendRequest(loginUser, receiverId, Status.REQUESTED);
        return new ResponseEntity<>(friendshipResponseDto, HttpStatus.CREATED);
    }

    // 친구 수락/거절 (누구의 친추 수락/거절을 할 것인지)
    @PatchMapping("/{requesterId}")
    public ResponseEntity<FriendshipResponseDto> handleAcceptFriendRequest(
            @PathVariable Long requesterId,
            @RequestBody FriendshipRequestDto dto,
            HttpServletRequest request
    ) {
        // 요청에 ACCEPTED, REJECTED 외에 다른 입력값이 들어올 경우 예외 처리 필요
        // 1이 1에게 친구 요청하는 경우 예외 처리 필요
        // 이미 친구 요청을 한 경우 예외 처리 필요
        // 로그인해놓고 같은 아이디로 다시 로그인하는 경우 예외 처리 필요

        // 로그인 세션 유지
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        // 서비스 로직을 통해 Dto 반환
        FriendshipResponseDto friendshipResponseDto = friendshipService.handleFriendRequest(loginUser, requesterId, dto.getStatus());

        return new ResponseEntity<>(friendshipResponseDto, HttpStatus.OK);
    }

    // 친구 삭제
    @DeleteMapping("/{requesterId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long requesterId, HttpServletRequest request) {

        User loginUser = (User) request.getSession().getAttribute("loginUser");

        friendshipService.removeFriend(loginUser, requesterId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
