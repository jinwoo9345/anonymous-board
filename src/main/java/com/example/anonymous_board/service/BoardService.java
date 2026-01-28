package com.example.anonymous_board.service;

import com.example.anonymous_board.dto.BoardDTO;
import com.example.anonymous_board.entity.Board;
import com.example.anonymous_board.entity.User;
import com.example.anonymous_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.example.anonymous_board.dto.BoardUpdateRequest;
import com.example.anonymous_board.dto.BoardWriteDto;
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 1. 글 작성 (Create)
    public void write(BoardWriteDto dto, User user) {

        // 1. 엔티티로 변환
        // 이제 작성자 이름은 'User 객체'에서 직접 꺼냅니다. (사칭 방지)
        Board board = new Board(
            dto.getTitle(),
            dto.getContent(),
            user.getNickName() // ★ 핵심: 로그인한 사람의 닉네임을 넣음!
        );

        // 2. DB에 저장
        boardRepository.save(board);
    }

    // 2. 전체 글 조회 (Read) - 일단 껍데기만 만들어둠
    public List<BoardDTO> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(BoardDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public BoardDTO getBoard(Long id) {
        return boardRepository.findById(id) // ID로 찾기
                .map(BoardDTO::fromEntity)  // 찾으면 DTO로 변환
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다.")); // 없으면 에러
    }
    // 3. 글 삭제 (Delete)
    public void deleteBoard(Long id, User user) {
        // 1. id로 글을 조회한다.(없으면 에러 던지기?)
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        
        //2. 글의 비밀번호 (board.getPassword())와 입력한 비밀번호 (password)가 일치하는지 확인한다.
        // 다르면 에러(Exception)을 낸다.
        if(!board.getAuthor().equals(user.getNickName())) {
            throw new IllegalArgumentException("작성자만 삭제 가능합니다.");
        }
        //3. 글을 삭제한다.
        boardRepository.delete(board);
    }
    
    @Transactional
    public Long update(Long id, BoardUpdateRequest requestDto, User user) {
        //1. 수정 할 글을 DB에서 조회 (없으면에러)
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        
        //2.찾은 게시글의 비밀번호 확인
        if(!board.getAuthor().equals(user.getNickName())) {
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }
        
        
        //3. 찾은 글의 내용을 수정
        board.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
}