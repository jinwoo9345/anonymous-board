package com.example.anonymous_board.dto;

import com.example.anonymous_board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data // Getter, Setter, ToString 자동 생성
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 생성자
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
 

    private LocalDateTime createdAt;

    // Entity -> DTO 변환 (조회용)
    public static BoardDTO fromEntity(Board board) {
        return new BoardDTO(
            board.getId(),
            board.getTitle(),
            board.getContent(),
            board.getAuthor(),
            board.getCreatedAt()
        );
    }
}