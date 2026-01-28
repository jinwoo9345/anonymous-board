package com.example.anonymous_board.service;

import org.springframework.stereotype.Service;

import com.example.anonymous_board.dto.UserLoginDto;
import com.example.anonymous_board.dto.UserSignUpDto;
import com.example.anonymous_board.repository.UserRepository;


import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.anonymous_board.entity.User;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;

    //1. 회원가입기능
    @Transactional
    public void signUp(UserSignUpDto dto){
        if(userRepository.findByUserId(dto.getUserId()).isPresent()){
            throw new  IllegalArgumentException("해당 아이디가 이미 존재합니다.");
        }

        //Dto-> Entity로 변환   
        User user = new User(dto.getUserId(),dto.getPassword(),dto.getNickName());

        // 변환 유저 entity 를 DB에 저장한다.
        userRepository.save(user);
    }


    // 2. 로그인기능

    public User login(UserLoginDto dto){

        //1. 입력된 아이디를 디비에서 검색
        User user = userRepository.findByUserId(dto.getUserId()).orElse(null);

        if(user == null || !user.getPassword().equals(dto.getPassword())){
            return null;
        }
        return user;
    }
}
