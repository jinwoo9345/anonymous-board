package com.example.anonymous_board.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users") //  테이블 이름을 users로 지정정
public class User {
    
    //1. 고유키 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //2. 로그인 아이디 
    @Column(nullable = false, unique = true)
    private String userId;

    //3. 비밀번호
    @Column(nullable = false)
    private String password;

    //4. 닉네임
    @Column(nullable = false)
    private String nickName;

    //5. 가입시간
    private LocalDateTime createAt;

    //6. 생성자
    public User(String userId,String password, String nickName){
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;

        this.createAt = LocalDateTime.now();
    }

}
