package com.example.anonymous_board.dto;

import com.example.anonymous_board.entity.Board;
import com.example.anonymous_board.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto {
    
    private Long boardId;    // 어느 게시글에 달 건지 (제일 중요!)
    private String author;   // 작성자
    private String content;  // 내용
    private String password; // 비밀번호

    // 💡 DTO(그릇)에 담긴 내용을 -> 진짜 알맹이(Entity)로 바꾸는 기능
    // (서비스에서 이 기능을 써서 DB에 저장할 거예요)
    public Comment toEntity(Board board) {
        return new Comment(this.author, this.content, this.password, board);
    }
}