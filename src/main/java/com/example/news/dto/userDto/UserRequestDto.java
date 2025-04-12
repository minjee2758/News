package com.example.news.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
public class UserRequestDto {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,20}$", message = "이메일 형식이 올바르지 않습니다.")
    @Email
    private final String email;
    @Size(min = 4, max = 4)
    private final String mbti;
    //    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$", message = "비밀번호는 4~10자, 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private final String password;
    private final String username;
    private final Long id;

    public UserRequestDto(String email, String password, String username, String mbti, Long id) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.mbti = mbti;
        this.id = id;
    }
}
