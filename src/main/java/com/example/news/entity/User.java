package com.example.news.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user")
@AllArgsConstructor
public class User extends BaseEntity {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMNET
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,20}$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 공백일 수 없습니다")
    private String email;

    @Size(min = 4, max = 4)
    private String mbti;

    @Column(nullable = false)
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$", message = "비밀번호는 4~10자, 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    private LocalDateTime withdrawTime;

    public User() {
    }

    public User(String username, String email, String mbti, String password) {
        this.username = username;
        this.email = email;
        this.mbti = mbti;
        this.password = password;
    }

}
