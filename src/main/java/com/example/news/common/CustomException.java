package com.example.news.common;

import lombok.Getter;

@Getter
public class  CustomException extends RuntimeException {
    private final Error error;

    public CustomException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
    public CustomException(Error error, String message) {
        super(message);
        this.error = error;
    }
}
