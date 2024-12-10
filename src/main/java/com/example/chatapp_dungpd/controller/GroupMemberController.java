package com.example.chatapp_dungpd.controller;

import com.example.chatapp_dungpd.model.GroupMember;
import com.example.chatapp_dungpd.model.GroupMemberId;
import com.example.chatapp_dungpd.repository.GroupMemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group-members")
public class GroupMemberController {


    private final GroupMemberRepository groupMemberRepository;

    public GroupMemberController(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }


    @PostMapping
    public ResponseEntity<GroupMember> addGroupMember(@RequestBody GroupMember groupMember) {
        GroupMember newMember = groupMemberRepository.save(groupMember);
        return ResponseEntity.ok(newMember);
    }

    @GetMapping("/chatroom/{chatroomId}")
    public ResponseEntity<List<GroupMember>> getMembersByChatroom(@PathVariable Long chatroomId) {
        List<GroupMember> members = groupMemberRepository.findAll(); // Thêm bộ lọc nếu cần
        return ResponseEntity.ok(members);
    }

    @DeleteMapping("/{channelId}/{userId}")
    public ResponseEntity<Void> removeGroupMember(@PathVariable Long channelId, @PathVariable Long userId) {
        GroupMemberId id = new GroupMemberId(channelId, userId);
        groupMemberRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
