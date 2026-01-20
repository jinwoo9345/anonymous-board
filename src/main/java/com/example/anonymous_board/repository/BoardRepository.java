package com.example.anonymous_board.repository;

import com.example.anonymous_board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

// <관리할 엔티티, PK 타입(Long)>
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 텅 비어있어도 save(), findAll(), findById(), deleteById() 다 됨!
}
