package com.example.anonymous_board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.anonymous_board.dto.CommentRequestDto;
import com.example.anonymous_board.dto.CommentResponseDto;
import com.example.anonymous_board.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    
    private final CommentService commentService;

    //1. 댓글 작성(Post)
    // 주소:POST /api/comment/create
    @PostMapping("/create")
    public CommentResponseDto create(@RequestBody CommentRequestDto commentRequestDto){
        return commentService.createComment(commentRequestDto);
    } 

    //2. 댓글 조회 (GET)
    // 주소: GET  /api/comment/list
    @GetMapping
    public java.util.List<CommentResponseDto> getList(@RequestParam Long boardId){
        return commentService.getComments(boardId);
    }
}
