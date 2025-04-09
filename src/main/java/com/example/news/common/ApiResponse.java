package com.example.news.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private final int code;        // 상태코드
    private final T data;          // 데이터
    private final String comment;  // 설명 메시지

    public static <T> ApiResponse<T> success(T data, String comment) {
        return new ApiResponse<>(200, data, comment);
    }

    public static <T> ApiResponse<T> created(T data, String comment) {
        return new ApiResponse<>(201, data, comment);
    }

    public static <T> ApiResponse<T> noContent(String comment) {
        return new ApiResponse<>(204, null, comment);
    }

    public static <T> ApiResponse<T> error(int code, String comment) {
        return new ApiResponse<>(code, null, comment);
    }
}

