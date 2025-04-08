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
    UPDATE_SUCCESS(HttpStatus.OK, 200, "수정이 완료되었습니다."),
    DELETE_SUCCESS(HttpStatus.OK, 200, "삭제가 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    SuccessCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
