package com.example.news.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    INVALID_LOGIN(HttpStatus.BAD_REQUEST, 200, "아이디 또는 비밀번호가 일치하지 않습니다."),
    REQUIRED_USER_EMAIL(HttpStatus.BAD_REQUEST,201, "이메일 입력은 필수입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,  203, "입력값이 잘못되었습니다");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    Error(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
