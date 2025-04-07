package com.example.news.service;

import com.example.news.config.PasswordEncoder;
import com.example.news.dto.userDto.UserResponseDto;
import com.example.news.entity.User;
import com.example.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto signUp(String email, String username, String mbti, String password) {

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, email, mbti, encodedPassword);

//        Optional<User> userOptional = userRepository.findById(user.getId());
//        if (userOptional.isPresent()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "유저가 이미 존재합니다.");
//        }
        User signUser = userRepository.save(user);

        return new UserResponseDto(signUser.getEmail(),signUser.getUsername(),signUser.getMbti());
    }
}
