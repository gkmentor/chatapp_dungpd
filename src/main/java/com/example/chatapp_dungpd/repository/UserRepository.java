package com.example.chatapp_dungpd.repository;

import com.example.chatapp_dungpd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
