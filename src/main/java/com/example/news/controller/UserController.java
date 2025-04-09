package com.example.news.controller;

import com.example.news.common.CommonResponse;
import com.example.news.common.SuccessCode;
import com.example.news.dto.userDto.UpdatePwResponseDto;
import com.example.news.dto.userDto.UserRequestDto;
import com.example.news.dto.userDto.UserResponseDto;
import com.example.news.entity.User;
import com.example.news.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
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
    public ResponseEntity<CommonResponse<UserResponseDto>> login(@Valid @RequestBody UserRequestDto dto,
                                                                 HttpServletRequest request) {
        UserResponseDto userResponseDto = userService.login(dto.getEmail(), dto.getPassword());
        User user = userService.findUserByEmail(dto.getEmail());

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);

        return ResponseEntity.status(SuccessCode.LOGIN_SUCCESS.getHttpStatus())
                .body(CommonResponse.of(SuccessCode.LOGIN_SUCCESS, userResponseDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<String>> logout(@Valid @RequestBody UserRequestDto dto, HttpServletRequest request) {
        UserResponseDto userResponseDto = userService.logout(dto.getEmail(), dto.getPassword());
        System.out.println(userResponseDto.getUsername()+"님 로그아웃");

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
            return ResponseEntity.status(SuccessCode.LOGOUT_SUCCESS.getHttpStatus())
                    .body(CommonResponse.of(SuccessCode.LOGOUT_SUCCESS, "success"));
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

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

    @GetMapping("/profile/{user_id}")
    public ResponseEntity<CommonResponse<UserResponseDto>> findUserById(@PathVariable Long user_id) {
        UserResponseDto userResponseDto = userService.findUserById(user_id);
        return ResponseEntity.status(SuccessCode.FIND_SUCCESS.getHttpStatus())
                .body(CommonResponse.of(SuccessCode.FIND_SUCCESS, userResponseDto));
    }

    @PatchMapping("/withdraw")
    public ResponseEntity<CommonResponse<String>> withdraw(@Valid @RequestBody UserRequestDto dto, HttpServletRequest request) {
        log.info("이메일&비번 정상 들어옴");
        userService.withdraw(dto.getEmail(), dto.getPassword());

        HttpSession session = request.getSession(false);
        session.invalidate();

        return ResponseEntity.status(SuccessCode.WITHDRAW_SUCCESS.getHttpStatus())
                .body(CommonResponse.of(SuccessCode.WITHDRAW_SUCCESS, "회원 탈퇴 완료"));
    }

}
