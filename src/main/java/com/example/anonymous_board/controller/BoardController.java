package com.example.anonymous_board.controller;

import com.example.anonymous_board.dto.BoardWriteDto;
import com.example.anonymous_board.dto.BoardDTO;
import com.example.anonymous_board.service.BoardService;
import com.example.anonymous_board.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
    public String write(@RequestBody BoardWriteDto dto, HttpServletRequest request) {
        // @RequestBody: "주문서(JSON)를 자바 객체(DTO)로 바꿔서 받아줘"

        //1단계 .: 검문소 :  세션이 있는지, 로그인은 했는지 확인
        HttpSession session  = request.getSession(false);
        if(session == null || session.getAttribute("loginUser")== null){
            return "로그인이 필요한 기능입니다!";
        }

        //2단계- 작성자 신원확인: 세션에서 유저 정보 꺼내기
        // (object로 저장되어있음)-> user로 강제 형변환 필요
        User loginUser = (User) session.getAttribute("loginUser");


        //3단계-서비스에게 전달
        //이 dto로 글을 써줘 . 글쓴이는 loginUser야
        boardService.write(dto,loginUser);

        return "게시글 작성 완료!!";
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
    @PutMapping("/update/{id}")
    public String update(
        @PathVariable Long id, 
        @RequestBody BoardUpdateRequest dto, 
        HttpServletRequest request
       
    ){
        // 1. 우선 세션체크 먼저(로그인 했는지 확인)
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("loginUser") == null){
            return "로그인이 필요한 기능입니다!";
        }

        User loginUser = (User) session.getAttribute("loginUser");
        try {
            //3. 서비스 호출(id, dto, 로그인 유저객체 전달!)
            boardService.update(id,dto,loginUser);
            return "글 수정 성공!";
        } catch (IllegalArgumentException e) {
            // "작성자만 수정가능" 과 유사한 에러메시지 반환
            return e.getMessage();
        }
    }
    //4. 글 삭제 (DELETE)
    @DeleteMapping("/delete/{id}")
    public String delete(
        @PathVariable("id") Long id,
        HttpServletRequest request
        
    ){
        //1. 세션체크 우선 
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("loginUser")== null){
            return "로그인이 필요한 기능입니다.";
        }

        User loginUser = (User) session.getAttribute("loginUser");
        try {
            //3. 삭제 서비스 호출 (id, 유저 객체 전달)
            boardService.deleteBoard(id, loginUser);

            return "글 삭제 성공!";
        } catch (Exception e) {
            // TODO: handle exception
            return e.getMessage();
        }
    }
}