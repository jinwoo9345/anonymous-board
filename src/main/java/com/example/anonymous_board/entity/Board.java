package com.example.anonymous_board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity // 1. DB 테이블이야!
@Table(name = "board_table") // 2. 테이블 이름 지정
@Getter // 3. 롬복: Getter 자동 생성
@ToString // 4. 롬복: 데이터 확인용
@NoArgsConstructor // 5. 롬복: 기본 생성자
public class Board {

    @Id // PK (주민번호)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    private Long id;

    @Column(length = 50, nullable = false) // 제목은 50자 제한, 필수 입력
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false) // 내용은 길게, 필수 입력
    private String content;

    @Column(length = 20, nullable = false) // 작성자는 20자 제한
    private String author;

    @Column(length = 20, nullable = false) // 🔐 핵심! 비밀번호 (수정/삭제용)
    private String password;

    private LocalDateTime createdAt; // 작성 시간

    // 우리가 쓸 생성자 (제목, 내용, 작성자, 비번만 있으면 됨)
    public Board(String title, String content, String author, String password) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.password = password;
        this.createdAt = LocalDateTime.now(); // 현재 시간 자동 저장
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}