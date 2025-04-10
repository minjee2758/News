package com.example.news.service;

import com.example.news.config.PasswordEncoder;
import com.example.news.dto.userDto.UserResponseDto;
import com.example.news.entity.User;
import com.example.news.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto signUp(String email, String username, String mbti, String password) {

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, email, mbti, encodedPassword);
        User signUser = userRepository.save(user);

        return new UserResponseDto(signUser.getEmail(),signUser.getUsername(),signUser.getMbti());
    }


//    @Override
//    public UserResponseDto login(String email, String password) {
//        User user = userRepository.findUserByEmailOrElseThrow(email);
//        if (user.getWithdrawTime() == null){
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                return new UserResponseDto(user.getEmail(), user.getUsername(), user.getMbti());
//            } else {
//                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이나 비밀번호가 잘못되었습니다");
//            }
//        } else{
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, user.getUsername()+"님은 탈퇴된 회원입니다");
//        }
//    }



    @Override
    public UserResponseDto logout(String email, String password) {
        User user = userRepository.findUserByEmailOrElseThrow(email);
        if (!passwordEncoder.matches(password, passwordEncoder.encode(password))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 잘못되었습니다");
        } else {
            return new UserResponseDto(user.getEmail(), user.getUsername(), user.getMbti());
        }
    }

    @Override
    @Transactional
    public boolean updatePw(String email, String password, String newPassword) {
        User user = userRepository.findUserByEmailOrElseThrow(email);

        if (passwordEncoder.matches(password, user.getPassword())) {
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존과 동일한 비밀번호는 입력할 수 없습니다.");
            }
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return true;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존 비밀번호 입력이 잘못되었습니다");
    }

    @Override
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findUserByIdOrElseThrow(id);

        return new UserResponseDto(user.getEmail(), user.getUsername(), user.getMbti());
    }



    @Override
    public void withdraw(String email, String password) {
        log.info("service로 들어옴");
        User user = userRepository.findUserByEmailOrElseThrow(email);
        log.info("회원 찾음");
        if (passwordEncoder.matches(password, user.getPassword())) {
            LocalDateTime withdrawTime = LocalDateTime.now();
            user.setWithdrawTime(withdrawTime);
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "회원정보가 일치하지 않습니다. 이메일이나 비밀번호를 확인하세요");
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmailOrElseThrow(email);
    }
}
