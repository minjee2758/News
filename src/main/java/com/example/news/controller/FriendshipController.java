package com.example.news.controller;

import com.example.news.common.CommonResponse;
import com.example.news.common.SuccessCode;
import com.example.news.dto.friendDto.FriendBoardResponseDto;
import com.example.news.dto.friendDto.FriendshipRequestDto;
import com.example.news.dto.friendDto.FriendshipResponseDto;
import com.example.news.dto.friendDto.FriendResponseDto;
import com.example.news.entity.Friendship;
import com.example.news.entity.User;
import com.example.news.service.BoardService;
import com.example.news.service.FriendshipService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friendship")
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final BoardService boardService;

    // 친구 목록 확인
    @GetMapping
    public ResponseEntity<CommonResponse<List<FriendResponseDto>>> getFriendList(
            HttpServletRequest request
    ) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        List<FriendResponseDto> friendList = friendshipService.getFriendList(loginUser);
        return ResponseEntity.ok(CommonResponse.of(SuccessCode.FIND_SUCCESS, friendList));
    }

    // 최근에 보낸 친구 요청 목록 확인
     @GetMapping("/requests/sent")
     public ResponseEntity<CommonResponse<List<FriendshipResponseDto>>> getSentFriendRequests(
             HttpServletRequest request
     ) {
         User loginUser = (User) request.getSession().getAttribute("loginUser");
         List<FriendshipResponseDto> sentFriendRequests = friendshipService.getSentFriendRequests(loginUser);
         return ResponseEntity.ok(CommonResponse.of(SuccessCode.FIND_SUCCESS,sentFriendRequests));
     }

    // 최근에 받은 친구 요청 목록 확인
    @GetMapping("/requests/received")
    public ResponseEntity<CommonResponse<List<FriendshipResponseDto>>> getReceivedFriendRequests(
            HttpServletRequest request
    ) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        List<FriendshipResponseDto> receivedFriendRequests = friendshipService.getReceivedFriendRequests(loginUser);
        return ResponseEntity.ok(CommonResponse.of(SuccessCode.FIND_SUCCESS,receivedFriendRequests));
    }

    // 친구의 포스팅 전체 조회 (최신순으로)
    @GetMapping("/{friendId}")
    public ResponseEntity<CommonResponse<List<FriendBoardResponseDto>>> getFriendPosts(
            @PathVariable Long friendId,
            HttpServletRequest request
    ) {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        List<FriendBoardResponseDto> friendPosts = boardService.getFriendPosts(loginUser.getId(), friendId);
//        return new ResponseEntity<>(friendPosts, HttpStatus.OK);
        return ResponseEntity.ok(CommonResponse.of(SuccessCode.FIND_SUCCESS, friendPosts));
    }

    // 친구 추가 요청 (누구에게 요청할 것인지)
    @PostMapping("/{receiverId}")
    public ResponseEntity<CommonResponse<FriendshipResponseDto>> handleSendFriendRequest
    (
            @PathVariable Long receiverId,
            HttpServletRequest request
    ) {
        // 로그인 세션 유지
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        // 로그인 정보가 null일 경우 예외 처리 필요

        FriendshipResponseDto friendshipResponseDto = friendshipService.sendFriendRequest(loginUser, receiverId, Friendship.Status.REQUESTED);
//        return new ResponseEntity<>(friendshipResponseDto, HttpStatus.CREATED);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponse.of(SuccessCode.REQUEST_FRIEND_SUCCESS, friendshipResponseDto));
    }

    // 친구 수락/거절 (누구의 친추 수락/거절을 할 것인지)
    @PatchMapping("/{requesterId}")
    public ResponseEntity<CommonResponse<FriendshipResponseDto>>handleAcceptFriendRequest(
            @PathVariable Long requesterId,
            @RequestBody FriendshipRequestDto dto,
            HttpServletRequest request
    ) {

        // 로그인 세션 유지
        User loginUser = (User) request.getSession().getAttribute("loginUser");

        // 서비스 로직을 통해 Dto 반환
        FriendshipResponseDto friendshipResponseDto = friendshipService.handleFriendRequest(loginUser, requesterId, dto.getStatus());

        // 친구 요청 수락/거절에 따라 다른 응답 코드 반환
        SuccessCode successCode = (dto.getStatus() == Friendship.Status.ACCEPTED)
                ? SuccessCode.ACCEPT_FRIEND_SUCCESS
                : SuccessCode.REJECT_FRIEND_SUCCESS;

        return ResponseEntity.ok(CommonResponse.of(successCode, friendshipResponseDto));
    }

    // 친구 삭제
    @DeleteMapping("/{requesterId}")
    public ResponseEntity<CommonResponse<Void>> removeFriend(@PathVariable Long requesterId, HttpServletRequest request) {

        User loginUser = (User) request.getSession().getAttribute("loginUser");

        friendshipService.removeFriend(loginUser, requesterId);

        return ResponseEntity.ok(CommonResponse.of(SuccessCode.DELETE_SUCCESS, null));

    }




}
