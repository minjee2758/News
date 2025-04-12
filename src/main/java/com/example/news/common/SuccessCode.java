package com.example.news.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
/*
* enum클래스를 사용해 커스텀 응답 만들기
* httpStatus, code, message 반환
* code는 그냥 우리끼리 알아보기 좋게 커스텀하는거
 */
@Getter
public enum SuccessCode {
    SIGNUP_SUCCESS(HttpStatus.CREATED, 200, "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, 201, "로그인 되었습니다"),
    LOGOUT_SUCCESS(HttpStatus.OK, 202, "로그아웃이 완료되었습니다"),
    UPDATE_PW_SUCCESS(HttpStatus.OK, 200, "비밀번호 수정이 완료되었습니다."),
    FIND_SUCCESS(HttpStatus.OK, 204, "find가 실행되었습니다"),
    WITHDRAW_SUCCESS(HttpStatus.OK, 205, "회원 탈퇴가 완료되었습니다"),
    DELETE_SUCCESS(HttpStatus.OK, 200, "삭제가 완료되었습니다."),
    REQUEST_FRIEND_SUCCESS(HttpStatus.CREATED, 201, "친구 요청이 완료되었습니다."),
    ACCEPT_FRIEND_SUCCESS(HttpStatus.OK, 200, "친구 수락이 완료되었습니다."),
    REJECT_FRIEND_SUCCESS(HttpStatus.OK, 200, "친구 거절이 완료되었습니다."),
    CREATE_COMMENT_SUCCESS(HttpStatus.OK, 200, "댓글 생성이 완료되었습니다."),
    UPDATE_COMMENT_SUCCESS(HttpStatus.OK, 200, "댓글 수정이 완료되었습니다."),

    LIKE_SUCCESS(HttpStatus.OK, 200, "좋아요가 반영되었습니다"),
    LIKE_DELETE_SUCCESS(HttpStatus.NO_CONTENT, 204, "좋아요 삭제되었습니다");


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    SuccessCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
