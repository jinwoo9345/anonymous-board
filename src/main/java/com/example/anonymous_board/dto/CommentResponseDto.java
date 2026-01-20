package com.example.anonymous_board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import com.example.anonymous_board.entity.Comment;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;          // 댓글 번호 (나중에 삭제할 때 필요함)
    private String author;    // 작성자
    private String content;   // 내용
    private LocalDateTime createdAt; // 작성일

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.author = comment.getAuthor();
        this.content = comment.getContent();
        this.createdAt = LocalDateTime.now();
    }    
}
