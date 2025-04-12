package com.example.news.exception;

import lombok.Getter;

@Getter
public class  CustomException extends RuntimeException {
    private final FailCode failCode;

    public CustomException(FailCode failCode) {
        super(failCode.getMessage());
        this.failCode = failCode;
    }
    public CustomException(FailCode failCode, String message) {
        super(message);
        this.failCode = failCode;
    }
}
