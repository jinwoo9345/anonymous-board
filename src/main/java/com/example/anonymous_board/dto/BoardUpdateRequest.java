package com.example.anonymous_board.dto;

public class BoardUpdateRequest {
    private String title;
    private String content;
    private String password;

    //Getter, Setter 또는 @Getter, @Setter필요
    public String getTitle(){ return title; }
    public String getContent(){ return content; }
    public String getPassword(){ return password; }
}
