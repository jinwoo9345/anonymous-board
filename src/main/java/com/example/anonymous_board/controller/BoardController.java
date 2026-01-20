package com.example.anonymous_board.controller;

import com.example.anonymous_board.dto.BoardDTO;
import com.example.anonymous_board.service.BoardService;
import com.example.anonymous_board.dto.BoardUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // "나 데이터만 주는 웨이터야!" (JSON 반환)
@RequestMapping("/api/board") // "내 구역은 /api/board 야"
@RequiredArgsConstructor // "쉐프(Service) 자동 주입해줘"
public class BoardController {

    private final BoardService boardService;

    // 1. 글 쓰기 (POST)
    // 주소: POST /api/board/write
    @PostMapping("/write")
    public BoardDTO write(@RequestBody BoardDTO boardDTO) {
        // @RequestBody: "주문서(JSON)를 자바 객체(DTO)로 바꿔서 받아줘"
        return boardService.writeBoard(boardDTO);
    }

    // 2. 전체 글 조회 (GET)
    // 주소: GET /api/board/list
    @GetMapping("/list")
    public java.util.List<BoardDTO> getList() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public BoardDTO getBoard(@PathVariable("id") Long id) {
        return boardService.getBoard(id);
    }

    //3. 글 수정 (PUT)
    @PutMapping("/{id}")
    public Long update(
        @PathVariable("id") Long id,
        @RequestBody BoardUpdateRequest requestDto
    ){
        return boardService.update(id, requestDto);
    }
    //4. 글 삭제 (DELETE)
    @DeleteMapping("/{id}")
    public String delete(
        @PathVariable("id") Long id,
        @RequestParam("password") String password
    ){
        boardService.deleteBoard(id, password);
        return "삭제 성공";
    }
}