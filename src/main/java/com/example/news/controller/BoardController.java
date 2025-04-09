package com.example.news.controller;

import com.example.news.common.ApiResponse;
import com.example.news.dto.boardDto.BoardRequestDto;
import com.example.news.dto.boardDto.BoardResponseDto;
import com.example.news.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<ApiResponse<BoardResponseDto>> createBoard(
            @RequestParam Long userId,
            @RequestBody BoardRequestDto dto) {
        BoardResponseDto response = boardService.createBoard(dto.getTitle(), dto.getContent(), userId);
        return ResponseEntity.status(201).body(ApiResponse.created(response, "게시글이 생성되었습니다"));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoard(
            @PathVariable Long boardId,
            @RequestParam Long userId,
            @RequestBody BoardRequestDto dto) {
        BoardResponseDto response = boardService.updateBoard(boardId, dto.getTitle(), dto.getContent(), userId);
        return ResponseEntity.ok(ApiResponse.success(response, "게시글이 수정되었습니다"));
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResponse<Void>> deleteBoard(
            @PathVariable Long boardId,
            @RequestParam Long userId) {
        boardService.deleteBoard(boardId, userId);
        return ResponseEntity.noContent().build(); // 메시지 없음
    }



    // 게시글 작성
//    @PostMapping
//    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
//        BoardResponseDto boardResponseDto = boardService.createBoard(
//                boardRequestDto.getTitle(),
//                boardRequestDto.getContent(),
//                boardRequestDto.getUserId() // 작성자 ID 전달
//        );
//        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
//    }


    //게시글 작성
//    @PostMapping
//    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
//        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto.getTitle(),boardRequestDto.getContent(),boardRequestDto.getUserId());
//        return new ResponseEntity<>(boardResponseDto,HttpStatus.CREATED);
//    }

    // 게시글 수정
//    @PutMapping("/{boardId}")
//    public ResponseEntity<BoardResponseDto> updateBoard(
//            @PathVariable Long boardId,
//            @RequestBody BoardRequestDto boardRequestDto
//    ) {
//        BoardResponseDto boardResponseDto = boardService.updateBoard(
//                boardId,
//                boardRequestDto.getTitle(),
//                boardRequestDto.getContent(),
//                boardRequestDto.getUserId()
//        );
//        return ResponseEntity.ok(boardResponseDto);
//    }


    // 게시글 삭제 - 작성자 본인만 가능
//    @DeleteMapping("/{boardId}")
//    public ResponseEntity<Void> deleteBoard(
//            @PathVariable Long boardId,
//            @RequestParam Long userId // 요청자 ID
//    ) {
//        boardService.deleteBoard(boardId, userId);
//        return ResponseEntity.noContent().build();
//    }

}
