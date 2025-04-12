package com.example.news.exception;

import com.example.news.common.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Custom Exception 처리 - Service 계층에서 발생한 비즈니스 예외 처리
     */
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<CommonResponse> handleCustomException(CustomException e) {
        log.error("CustomException: {}", e.getMessage());
        FailCode failCode = e.getFailCode();
        CommonResponse response = CommonResponse.from(failCode, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(failCode.getHttpStatus().value()));
    }

    //@RequestBody에서 바인딩된 DTO의 @Vaild에서 오류가 생겼을때
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CommonResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage());
        List<CommonResponse.FieldError> fieldErrors = processFieldErrors(e.getBindingResult());
        CommonResponse response = CommonResponse.from(FailCode.INVALID_INPUT_VALUE, fieldErrors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //@RequestParam @PathVariable 등의 파라미터 유효성 검사 실패했을떄
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<CommonResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException: {}", e.getMessage());
        List<CommonResponse.FieldError> fieldErrors = e.getConstraintViolations().stream().map(
                constraintViolation -> CommonResponse.FieldError.of(
                        constraintViolation.getPropertyPath().toString(),
                        constraintViolation.getInvalidValue() != null ? constraintViolation.getInvalidValue().toString() : "",
                        constraintViolation.getMessage()
                )
        ).collect(Collectors.toList());
        CommonResponse response = CommonResponse.from(FailCode.INVALID_INPUT_VALUE, fieldErrors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private List<CommonResponse.FieldError> processFieldErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error -> CommonResponse.FieldError.of(
                        error.getField(),
                        error.getRejectedValue() != null ? error.getRejectedValue().toString() : "",
                        error.getDefaultMessage()
                ))
                .collect(Collectors.toList());
    }
}



