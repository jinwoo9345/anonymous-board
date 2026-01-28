package com.example.anonymous_board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardWriteDto {
    // 사용자는 딱 이 2개만 보냅니다.
    // 작성자(author)는 세션에서 꺼내고, 비밀번호는 이제 필요 없습니다!
    private String title;
    private String content;
}