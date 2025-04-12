package com.example.news.service;

import com.example.news.exception.CustomException;
import com.example.news.exception.FailCode;
import com.example.news.config.PasswordEncoder;
import com.example.news.dto.userDto.UserResponseDto;
import com.example.news.entity.User;
import com.example.news.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isPresent()) {
            throw new CustomException(FailCode.ALREADY_EXIST_USER);
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, email, mbti, encodedPassword);
        User signUser = userRepository.save(user);

        return new UserResponseDto(signUser.getEmail(),signUser.getUsername(),signUser.getMbti());
    }


    @Override
    public UserResponseDto login(String email, String password) {
        User user = userRepository.findUserByEmailOrElseThrow(email);
        if (user.getWithdrawTime() == null){
            if (passwordEncoder.matches(password, user.getPassword())) {
                return new UserResponseDto(user.getEmail(), user.getUsername(), user.getMbti());
            } else {
                throw new CustomException(FailCode.INVALID_LOGIN);
            }
        } else{
            log.info("탈퇴된 회원 조회");
            throw new CustomException(FailCode.INVALID_LOGIN);
        }

    }



    @Override
    public UserResponseDto logout(String email, String password) {
        User user = userRepository.findUserByEmailOrElseThrow(email);
        if (!passwordEncoder.matches(password, passwordEncoder.encode(password))) {
            throw new CustomException(FailCode.INVALID_INPUT_VALUE);
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
                throw new CustomException(FailCode.UNCHANGED_PASSWORD);
            }
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return true;
        }
        throw new CustomException(FailCode.INVALID_PASSWORD_INPUT);
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
            throw new CustomException(FailCode.INVALID_LOGIN);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmailOrElseThrow(email);
    }
}
