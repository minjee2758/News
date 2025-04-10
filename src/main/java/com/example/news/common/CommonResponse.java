package com.example.news.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/*
* 제네릭으로 만들어서, 어떤 Response타입이 와도 처리할 . 있게 함
* 만약 제네릭으로 하지 않으면 response타입마다 따로 만들어야함 -> 생산성 최악악악으악
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private final int code;
    private final T data;
    private final String comment;

    //성공 처리
    public static <T> CommonResponse<T> of(SuccessCode code, T data) {
        return CommonResponse.<T>builder()
                .code(code.getCode())
                .data(data)
                .comment(code.getMessage())
                .build();
    }

    //에러 처리
    public static <T> CommonResponse<T> from(Error error, T data) {
        return CommonResponse.<T>builder()
                .code(error.getCode())
                .data(data)
                .comment(error.getMessage())
                .build();
    }

    @Getter
    @Builder
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        public static FieldError of(String field, String value, String reason) {
            return FieldError.builder()
                    .field(field)
                    .value(value)
                    .reason(reason)
                    .build();
        }
    }
}
