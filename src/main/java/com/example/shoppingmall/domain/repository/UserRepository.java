package com.example.shoppingmall.domain.repository;

import com.example.shoppingmall.domain.entity.User;
import com.example.shoppingmall.web.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
