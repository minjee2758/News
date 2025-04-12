package com.example.news.exception;

import lombok.Getter;

@Getter
public class  CustomException extends RuntimeException {
    private final FailCode error;

    public CustomException(FailCode error) {
        super(error.getMessage());
        this.error = error;
    }
    public CustomException(FailCode error, String message) {
        super(message);
        this.error = error;
    }
}
