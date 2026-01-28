package com.example.anonymous_board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.anonymous_board.dto.UserLoginDto;
import com.example.anonymous_board.dto.UserSignUpDto;
import com.example.anonymous_board.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.anonymous_board.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController{
    private final UserService userService;

    //1. 회원가입
    @PostMapping("/signup")
    public String signup(@RequestBody UserSignUpDto dto){
        //서비스의 회원가입 메소드 호출
        userService.signUp(dto);

        return "회원가입에 성공했습니다!";
    }

    //2. 로그인
    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto dto, HttpServletRequest request){
        
        User loginUser = userService.login(dto);

        if(loginUser == null){
            return "로그인 실패! \n아이디 또는 비밀번호가 잘못되었습니다! 다시 확인해주세요";
        }
        
        // 세션 처리
        //1. 세션을 가져온다(없으면 새로 생성)
        HttpSession session = request.getSession();
        //2. 세션에 정보 저장하기
        session.setAttribute("loginUser", loginUser);

        return "로그인 성공!";

    }

    //3. 로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        //세션을 가져온다 (false: 없으면 새로 만들지 말고 그냥 null처리)
        HttpSession session = request.getSession(false);
        if(session != null){
            // 로그아웃하는 주요 메소드
            session.invalidate();
        }
        return "로그아웃 성공!";
    }

    //4. 로그인 사용자 정보 요청
    @GetMapping("/info")
    public Object getUserInfo(HttpServletRequest rquest){
        HttpSession session = rquest.getSession(false);

        //세션이 없거나, 로그인 정보가 없다면 -> null을 반환 or 에러메시지
        if(session == null || session.getAttribute("loginUser")==null){
            return null;
        }

        //세션에서 꺼내기 (Object로 저장되어있어 (User)로 강제 형변환 필요)
        User loginUser = (User) session.getAttribute("loginUser");

        //비밀번호 같은 건 빼고 , 안전하게 Dto에 담아서 준다
        return new com.example.anonymous_board.dto.UserResponseDto(loginUser);
    }
}