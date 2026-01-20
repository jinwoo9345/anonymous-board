package com.example.anonymous_board.service;

import com.example.anonymous_board.dto.BoardDTO;
import com.example.anonymous_board.entity.Board;
import com.example.anonymous_board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.example.anonymous_board.dto.BoardUpdateRequest;
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 1. 글 작성 (Create)
    public BoardDTO writeBoard(BoardDTO dto) {
        // DTO(도시락) -> Entity(재료)로 변환
        Board board = new Board(dto.getTitle(), dto.getContent(), dto.getAuthor(), dto.getPassword());
        
        // DB 저장
        Board savedBoard = boardRepository.save(board);
        
        // 저장된 결과 다시 DTO로 변환해서 반환
        return BoardDTO.fromEntity(savedBoard);
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
    public void deleteBoard(Long id, String password) {
        // 1. id로 글을 조회한다.(없으면 에러 던지기?)
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        
        //2. 글의 비밀번호 (board.getPassword())와 입력한 비밀번호 (password)가 일치하는지 확인한다.
        // 다르면 에러(Exception)을 낸다.
        if(!board.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //3. 글을 삭제한다.
        boardRepository.delete(board);
    }
    
    @Transactional
    public Long update(Long id, BoardUpdateRequest requestDto) {
        //1. 수정 할 글을 DB에서 조회 (없으면에러)
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        
        //2.찾은 게시글의 비밀번호 확인
        if(!board.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        
        
        //3. 찾은 글의 내용을 수정
        board.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }
}