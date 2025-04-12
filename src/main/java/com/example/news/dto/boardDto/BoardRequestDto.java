package com.example.news.dto.boardDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true) //final인데도 null로 강제 초기화
//DTO처럼 불변 객체로 만들고 싶은데, 기본 생성자도 꼭 필요한 경우 사용
@AllArgsConstructor
public class BoardRequestDto {

    private final String title;
    private final String content;


}
