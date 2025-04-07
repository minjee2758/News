package com.example.news.controller;

import com.example.news.dto.userDto.UserRequestDto;
import com.example.news.dto.userDto.UserResponseDto;
import com.example.news.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@Valid @RequestBody UserRequestDto dto) {

        UserResponseDto userResponseDto = userService.signUp(dto.getEmail(),dto.getUsername(),dto.getMbti(),dto.getPassword());

        return new ResponseEntity<>(userResponseDto,HttpStatus.CREATED);
    }

}
