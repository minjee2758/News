package com.example.news.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FailCode {
    INVALID_LOGIN(HttpStatus.BAD_REQUEST, 400, "아이디 또는 비밀번호가 일치하지 않습니다."),
    REQUIRED_USER_EMAIL(HttpStatus.BAD_REQUEST,401, "이메일 입력은 필수입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST,  401, "입력값이 잘못되었습니다");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    FailCode(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
