package com.example.news.controller;

import com.example.news.dto.userDto.UserRequestDto;
import com.example.news.dto.userDto.UserResponseDto;
import com.example.news.entity.User;
import com.example.news.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@Valid @RequestBody UserRequestDto dto) {

        UserResponseDto userResponseDto = userService.signUp(dto.getEmail(),dto.getUsername(),dto.getMbti(),dto.getPassword());

        return new ResponseEntity<>(userResponseDto,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserRequestDto dto) {
        User user = userService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.ok(user.getUsername()+"님 로그인되었습니다");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody UserRequestDto dto) {
        userService.logout(dto.getPassword());
        return ResponseEntity.ok("로그아웃되었습니다");
    }

    @PutMapping("/")
    public ResponseEntity<UserResponseDto> updatePw(@Valid @RequestBody UserRequestDto dto) {

    }

}
