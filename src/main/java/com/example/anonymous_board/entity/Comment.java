package com.example.anonymous_board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_table") // 보통 DB 테이블명은 소문자 스네이크 케이스를 씁니다
@Getter
@ToString(exclude = "board") // ★★★ 중요: Board 정보는 로그 찍지 마! (무한루프 방지)
@NoArgsConstructor
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 20, nullable = false)
    private String password;

    private LocalDateTime createdAt;

    // ▼▼▼ 여기가 추가된 핵심입니다! ▼▼▼
    @ManyToOne(fetch = FetchType.LAZY) // 게시글(1) : 댓글(N) 관계
    @JoinColumn(name = "board_id")     // DB에 생성될 컬럼 이름 (FK)
    private Board board;
    // ▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲

    // 생성자에도 board를 받아야 저장할 수 있겠죠?
    public Comment(String author, String content, String password, Board board) {
        this.author = author;
        this.content = content;
        this.password = password;
        this.board = board; // ★ 게시글 정보 저장
        this.createdAt = LocalDateTime.now();
    }
}