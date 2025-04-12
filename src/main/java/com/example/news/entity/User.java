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

@Getter
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
    private String email;

    private String mbti;

    @Column(nullable = false)
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
