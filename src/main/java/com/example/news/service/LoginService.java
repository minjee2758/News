package com.example.news.service;

import com.example.news.config.PasswordEncoder;
import com.example.news.dto.userDto.UserResponseDto;
import com.example.news.entity.User;
import com.example.news.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/*
* 세션 남기기, 로그인 횟수제한, 만료시간 지정의 모든 로그인 로직을 담당하는 클래스
* 단일책임원칙에 따르도록 함!!
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto login(String email, String password, HttpSession session) {
        User user = userRepository.findUserByEmailOrElseThrow(email);
        withdrawUser(email);
        if (passwordEncoder.matches(password, user.getPassword())) {
            session.setAttribute("loginUser", user);
            session.setAttribute("loginFailCount", 0);
            return new UserResponseDto(user.getEmail(), user.getUsername(), user.getMbti());
        }else {
            loginFail(session);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바르지 않은 로그인입니다. 5회 이상 입력이 틀리면 로그인이 제한됩니다.");
        }
    }

    public void withdrawUser(String email){
        User user = userRepository.findUserByEmailOrElseThrow(email);
        if (user.getWithdrawTime() != null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "탈퇴한 회원입니다");
        }
    }

    public void loginFail(HttpSession session) {
        Integer failCount = (Integer) session.getAttribute("loginFailCount");
        loginBlock(session);
        if (failCount == null) {
            failCount = 0;
        }
        failCount++;
        session.setAttribute("loginFailCount", failCount);
    }

    public void loginBlock(HttpSession session) {
        Integer failCount = (Integer) session.getAttribute("loginFailCount");
        if (failCount >5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
