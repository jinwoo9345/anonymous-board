package com.example.anonymous_board.dto;

import com.example.anonymous_board.entity.User;
import lombok.Getter;


@Getter
public class UserResponseDto {
    
    private String userId;
    private String nickName;

    public UserResponseDto(User user){
        this.userId = user.getUserId();
        this.nickName = user.getNickName();
    }

}
