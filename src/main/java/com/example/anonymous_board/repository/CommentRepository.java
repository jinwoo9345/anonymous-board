package com.example.anonymous_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.anonymous_board.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment , Long> {

    List<Comment> findByBoardId(Long boardId);
}