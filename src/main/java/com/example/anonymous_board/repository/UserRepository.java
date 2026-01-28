package com.example.anonymous_board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.anonymous_board.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUserId(String userId);
    
}
