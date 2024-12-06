package com.example.chatapp_dungpd.controller;

import com.example.chatapp_dungpd.model.Status;
import com.example.chatapp_dungpd.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusRepository statusRepository;

    @PostMapping
    public ResponseEntity<Status> createStatus(@RequestBody Status status) {
        Status newStatus = statusRepository.save(status);
        return ResponseEntity.ok(newStatus);
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuses() {
        return ResponseEntity.ok(statusRepository.findAll());
    }
}