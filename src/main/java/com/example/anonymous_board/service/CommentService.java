package com.example.anonymous_board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.anonymous_board.dto.CommentRequestDto;
import com.example.anonymous_board.dto.CommentResponseDto;
import com.example.anonymous_board.repository.BoardRepository;
import com.example.anonymous_board.repository.CommentRepository;
import com.example.anonymous_board.entity.Comment;
import com.example.anonymous_board.entity.User;
import com.example.anonymous_board.entity.Board;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CommentService {
    
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    //1. 댓글 작성(create)
    public CommentResponseDto createComment(CommentRequestDto dto, User user){

        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수 없습니다."));


        Comment comment = dto.toEntity(user.getNickName(), board);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    //2. 댓글 조회(GET)

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long boardId) {
        
        // 1. 리포지토리한테 "이 게시글 번호(boardId) 달고 있는 애들 다 찾아와" 명령
        List<Comment> comments = commentRepository.findByBoardId(boardId);

        // 2. 가져온 엔티티 리스트를 -> DTO 리스트로 변환 (포장하기)
        // (어려워 보이지만 "리스트 안에 있는 거 하나씩 꺼내서 DTO로 바꾼다"는 뜻입니다)
        return comments.stream()
                .map(CommentResponseDto::new) // 생성자로 변환
                .collect(Collectors.toList());
    }

    //3. 댓글 삭제
    public String deleteComment(Long commentId, User user){
        
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(()-> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        if(!comment.getAuthor().equals(user.getNickName())){
            throw new IllegalArgumentException("작성자만 삭제 가능합니다."); 
        }

        commentRepository.delete(comment);

        return "삭제가 완료되었습니다!";
    }

    // 4. 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto dto ,User user){

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(()->new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        
        if(!comment.getAuthor().equals(user.getNickName())){
            throw new IllegalArgumentException("작성자만 삭제 가능합니다");
        }

        // 저장은 따로 필요없음 자동으로 @Transactional 어노테이션이 저장
        comment.update(dto.getContent());

        return new CommentResponseDto(comment); // 수정된 내용 반환환
    }

}
