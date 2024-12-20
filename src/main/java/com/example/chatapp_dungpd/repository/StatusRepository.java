package com.example.chatapp_dungpd.repository;

import com.example.chatapp_dungpd.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByStatusName(String statusName);


}
