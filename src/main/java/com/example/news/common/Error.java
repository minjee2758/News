package com.example.news.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    INVALID_LOGIN(HttpStatus.UNAUTHORIZED, 401, "아이디 또는 비밀번호가 일치하지 않습니다."),
    REQUIRED_USER_EMAIL(HttpStatus.BAD_REQUEST, 400, "이메일 입력은 필수입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 400, "입력값이 잘못되었습니다"),
    UNCHANGED_PASSWORD(HttpStatus.FORBIDDEN, 403, "기존과 같은 비밀번호 입력입니다"),
    INVALID_PASSWORD_INPUT(HttpStatus.UNAUTHORIZED, 401, "비밀번호가 일치하지 않습니다"),
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, 401, "로그인 해야합니다"),

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, 400, "올바르지 않은 요청 상태입니다."),
    INVALID_FRIEND_REQUEST(HttpStatus.BAD_REQUEST, 400, "자기 자신에게 친구 요청을 보낼 수 없습니다."),
    ALREADY_REQUEST(HttpStatus.NOT_ACCEPTABLE, 406, "이미 친구 요청을 보냈습니다."),
    INVALID_FRIEND_ACCEPT(HttpStatus.BAD_REQUEST, 400, "자기 자신에게 친구 수락/거절을 할 수 없습니다."),
    NO_REQUEST(HttpStatus.NOT_FOUND, 404, "친구 요청이 존재하지 않습니다."),

    ONLY_AUTHOR_CAN_MODIFY(HttpStatus.FORBIDDEN, 403, "작성자만 수정할 수 있습니다."),
    ONLY_AUTHOR_CAN_DELETE(HttpStatus.FORBIDDEN, 403, "작성자만 삭제할 수 있습니다."),
    NOT_USER_POST(HttpStatus.FORBIDDEN, 403, "해당 유저의 게시글이 아닙니다."),
    FRIENDSHIP_NOT_ACCEPTED(HttpStatus.FORBIDDEN, 403, "친구 관계가 수락되지 않았습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "해당 유저를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "해당 게시글을 찾을 수 없습니다."),

    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    Error(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
