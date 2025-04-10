package com.example.news.service;

import com.example.news.config.PasswordEncoder;
import com.example.news.dto.userDto.UserResponseDto;
import com.example.news.entity.User;
import com.example.news.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/*
* 세션 남기기, 로그인 횟수제한, 만료시간 지정의 모든 로그인 로직을 담당하는 클래스
* 단일책임원칙에 따르도록 함!!
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto login(String email, String password, HttpSession session) {
        loginBlock(session); //로그인 로직이 실행되기 전에, 블락여부 판단하기
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
        log.info(failCount+ "번째 로그인 실패");

        session.setAttribute("loginFailCount", failCount);
        if (failCount == 3) {
            // 차단 시간 시작 지점 저장 (딱 3번째 실패 시점에)
            session.setAttribute("loginFailTime", System.currentTimeMillis());
        }
    }

    /*
    * "현재 시간 - 로그인 실패 시점 < 차단 시간 " 이면 현재 차단되어있는 상태이므로 실패 반환
    * 그게 아니라면 실패 횟수와 실패 시점 초기화 -> 로그인 시도할 수 있게
     */

    public void loginBlock(HttpSession session) {
        Integer failCount = (Integer) session.getAttribute("loginFailCount");
        Long failTime = (Long) session.getAttribute("loginFailTime");
        if (failCount == null) { failCount =0;}

        if (failCount ==null ||failCount<3) return;
        long blockTime = 10 * 1000; //10초
        long currentTime = System.currentTimeMillis();

        if (failTime !=null&& (currentTime-failTime)< blockTime) {
            long remainTime = (blockTime - (currentTime-failTime)) / 1000;
            log.info(remainTime+"초 동안은 로그인할 수 없습니다");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        //로그인 정상적으로 이루어지면 초기화해주기
        session.setAttribute("loginFailCount", 0);
        session.removeAttribute("loginFailTime");
    }
}
