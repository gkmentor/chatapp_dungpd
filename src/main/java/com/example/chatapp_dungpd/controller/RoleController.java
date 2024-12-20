package com.example.chatapp_dungpd.controller;

import com.example.chatapp_dungpd.exception.ResourceNotFoundException;
import com.example.chatapp_dungpd.model.Role;
import com.example.chatapp_dungpd.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {


    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role newRole = roleRepository.save(role);
        return ResponseEntity.ok(newRole);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
        return ResponseEntity.ok(role);
    }
}
