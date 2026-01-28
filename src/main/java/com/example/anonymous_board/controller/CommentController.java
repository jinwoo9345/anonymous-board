package com.example.anonymous_board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.anonymous_board.dto.CommentRequestDto;
import com.example.anonymous_board.dto.CommentResponseDto;
import com.example.anonymous_board.entity.User;
import com.example.anonymous_board.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;

    //1. 댓글 작성(Post)
    // 주소:POST /api/comment/create
    @PostMapping("/create")
    public CommentResponseDto create(
        @RequestBody CommentRequestDto dto,
        HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("loginUser")==null){
            throw new IllegalArgumentException("로그인이 필요한 기능입니다!");
        }

        User loginUser = (User)session.getAttribute("loginUser");
       
        return commentService.createComment(dto,loginUser);
    } 

    //2. 댓글 조회 (GET)
    // 주소: GET  /api/comment/list
    @GetMapping("/list")
    public java.util.List<CommentResponseDto> getList(@RequestParam Long boardId){
        return commentService.getComments(boardId);
    }

    // 3. 댓글 삭제
    // 주소:Get /api/comment/delete/{id}
    @DeleteMapping("/{id}")
    public String delete(
        @PathVariable("id") Long id,
        HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);
         

        if(session == null || session.getAttribute("loginUser")== null){
            return "로그인이 필요한 기능입니다!";
        }

        User loginUser = (User) session.getAttribute("loginUser");
        
        try {
            commentService.deleteComment(id, loginUser);
            return "삭제 성공!";
        } catch (IllegalArgumentException e) {
           return e.getMessage();
        }
        
    }

    // 4. 댓글 수정
    @PutMapping("/{id}")
    public String update(
        @PathVariable("id")  Long id,
        @RequestBody CommentRequestDto dto,
        HttpServletRequest request
    ){
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("loginUser") == null){
            return "로그인이 필요한 기능입니다!";
        }

        User loginUser = (User) session.getAttribute("loginUser");

        

        try {
            commentService.updateComment(id, dto, loginUser);
            return "수정 완료!!";
        } catch (IllegalArgumentException e) {
               // TODO: handle exception
            return e.getMessage();
        }
    }
}
