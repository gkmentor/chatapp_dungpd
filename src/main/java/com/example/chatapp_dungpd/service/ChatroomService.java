package com.example.chatapp_dungpd.service;

import com.example.chatapp_dungpd.model.Chatroom;
import com.example.chatapp_dungpd.model.GroupMember;
import com.example.chatapp_dungpd.repository.ChatroomRepository;
import com.example.chatapp_dungpd.repository.GroupMemberRepository;
import com.example.chatapp_dungpd.repository.RoleRepository;
import com.example.chatapp_dungpd.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final RoleRepository roleRepository;

    public ChatroomService(ChatroomRepository chatroomRepository, GroupMemberRepository groupMemberRepository, RoleRepository roleRepository) {
        this.chatroomRepository = chatroomRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.roleRepository = roleRepository;
    }

    // Tạo phòng chat nhóm (group chat)
    public Chatroom createChatroom(Long userId, String channelName) {
        Chatroom chatroom = new Chatroom();
        chatroom.setCreatedUserId(userId);
        chatroom.setChannelName(channelName);
        chatroom.setIsDirectedChat(false); // Không phải chat trực tiếp
        return chatroomRepository.save(chatroom);
    }

    // Tạo phòng chat trực tiếp giữa 2 người dùng
    public Chatroom createDirectedChat(Long userId1, Long userId2) {
        // Không cho phép tạo phòng chat với chính mình
        if (userId1.equals(userId2)) {
            throw new IllegalArgumentException("Cannot create a directed chat with the same user.");
        }

        // Kiểm tra nếu đã tồn tại phòng chat trực tiếp giữa 2 người
        Optional<Chatroom> existingChatroom = findExistingDirectedChat(userId1, userId2);
        if (existingChatroom.isPresent()) {
            return existingChatroom.get(); // Sử dụng lại phòng chat cũ
        }

        // Tạo mới nếu chưa tồn tại
        Chatroom chatroom = new Chatroom();
        chatroom.setCreatedUserId(userId1);
        chatroom.setIsDirectedChat(true); // Là phòng chat trực tiếp
        Chatroom savedChatroom = chatroomRepository.save(chatroom);

        // Thêm cả 2 người dùng vào phòng chat
        addUserToChatroom(userId1, savedChatroom.getChatroomId());
        addUserToChatroom(userId2, savedChatroom.getChatroomId());

        return savedChatroom;
    }

    // Thêm user vào phòng chat
    public GroupMember addUserToChatroom(Long userId, Long chatroomId) {
        // Gán vai trò mặc định cho user
        Long defaultRoleId = roleRepository.findByRoleName("MEMBER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"))
                .getRoleId();

        // Tạo và lưu thành viên phòng chat
        GroupMember groupMember = new GroupMember(chatroomId, userId, defaultRoleId);
        return groupMemberRepository.save(groupMember);
    }

    // Kiểm tra quyền của người dùng trong phòng
    public boolean isUserAdminOrOwner(Long userId, Long chatroomId) {
        return groupMemberRepository.findByChannelIdAndUserId(chatroomId, userId)
                .map(groupMember -> {
                    String roleName = groupMember.getRole().getRoleName(); // Sử dụng getRole() để lấy Role
                    return "ADMIN".equals(roleName) || "OWNER".equals(roleName);
                })
                .orElse(false);
    }

    // Kiểm tra phòng chat trực tiếp đã tồn tại giữa 2 user chưa
    private Optional<Chatroom> findExistingDirectedChat(Long userId1, Long userId2) {
        List<Chatroom> directChatrooms = chatroomRepository.findDirectedChatroomsByUserIds(userId1, userId2);
        return directChatrooms.stream()
                .filter(chatroom -> groupMemberRepository.countMembersByChatroomId(chatroom.getChatroomId()) == 2)
                .findFirst();
    }

    // Xóa phòng chat (chỉ ADMIN hoặc OWNER mới được phép)
    public void deleteChatroom(Long chatroomId, Long userId) throws IllegalAccessException {
        if (!isUserAdminOrOwner(userId, chatroomId)) {
            throw new IllegalAccessException("You do not have permission to delete this chatroom.");
        }
        chatroomRepository.deleteById(chatroomId);
    }

    public List<Chatroom> getAllChatrooms() {
        List<Chatroom> chatrooms = chatroomRepository.findAll();

        if (chatrooms.isEmpty()) {
            throw new ResourceNotFoundException("No chatrooms found.");
        }

        return chatrooms;
    }
}
