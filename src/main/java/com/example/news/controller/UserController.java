package com.example.news.controller;

import com.example.news.common.CommonResponse;
import com.example.news.common.SuccessCode;
import com.example.news.dto.userDto.UpdatePwResponseDto;
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
@RequestMapping("/news")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<UserResponseDto>> signUp(@Valid @RequestBody UserRequestDto dto) {

        UserResponseDto userResponseDto = userService.signUp(dto.getEmail(),dto.getUsername(),dto.getMbti(),dto.getPassword());

        return ResponseEntity.status(SuccessCode.SIGNUP_SUCCESS.getHttpStatus())
                .body(CommonResponse.of(SuccessCode.SIGNUP_SUCCESS, userResponseDto));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserResponseDto>> login(@Valid @RequestBody UserRequestDto dto) {
        UserResponseDto userResponseDto = userService.login(dto.getEmail(), dto.getPassword());
        return ResponseEntity.status(SuccessCode.LOGIN_SUCCESS.getHttpStatus())
                .body(CommonResponse.of(SuccessCode.LOGIN_SUCCESS, userResponseDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<String>> logout(@Valid @RequestBody UserRequestDto dto) {
        UserResponseDto userResponseDto = userService.logout(dto.getEmail(), dto.getPassword());
        System.out.println(userResponseDto.getUsername()+"님 로그아웃");
        return ResponseEntity.status(SuccessCode.LOGOUT_SUCCESS.getHttpStatus())
                .body(CommonResponse.of(SuccessCode.LOGOUT_SUCCESS, "success"));
    }

    @PatchMapping
    public ResponseEntity<CommonResponse<Boolean>> updatePw(@Valid @RequestBody UpdatePwResponseDto dto) {
        boolean isSame = userService.updatePw(dto.getEmail(), dto.getPassword(), dto.getNewPassword());

        if(isSame){
            return ResponseEntity.status(SuccessCode.UPDATE_PW_SUCCESS.getHttpStatus())
                    .body(CommonResponse.of(SuccessCode.UPDATE_PW_SUCCESS, true));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
