package com.example.chatapp_dungpd.service;

import com.example.chatapp_dungpd.model.Chatroom;
import com.example.chatapp_dungpd.model.GroupMember;
import com.example.chatapp_dungpd.model.Role;
import com.example.chatapp_dungpd.model.User;
import com.example.chatapp_dungpd.repository.ChatroomRepository;
import com.example.chatapp_dungpd.repository.GroupMemberRepository;
import com.example.chatapp_dungpd.repository.RoleRepository;
import com.example.chatapp_dungpd.exception.ResourceNotFoundException;
import com.example.chatapp_dungpd.repository.UserRepository;
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
    private final UserRepository userRepository;
    public ChatroomService(ChatroomRepository chatroomRepository, GroupMemberRepository groupMemberRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.chatroomRepository = chatroomRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    // Tạo phòng chat nhóm group chat
    public Chatroom createChatroom(Long userId, String channelName) {
        Chatroom chatroom = new Chatroom();

        // Lấy đối tượng User từ userId
        User user1 = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        chatroom.setCreatedUser(user1);
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

        // Lấy đối tượng User từ userId
        User user1 = userRepository.findById(userId1)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId1));

        // Tạo mới nếu chưa tồn tại
        Chatroom chatroom = new Chatroom();
        chatroom.setCreatedUser(user1); // Sử dụng đối tượng User thay cho kiểu Long
        chatroom.setIsDirectedChat(true); // Là phòng chat trực tiếp
        Chatroom savedChatroom = chatroomRepository.save(chatroom);

        // Thêm cả 2 người dùng vào phòng chat
        addUserToChatroom(userId1, savedChatroom.getChatroomId());
        addUserToChatroom(userId2, savedChatroom.getChatroomId());

        return savedChatroom;
    }

    // Thêm user vào phòng chat
    public GroupMember addUserToChatroom(Long userId, Long chatroomId) {
        // Fetch the default role
        Role defaultRole = roleRepository.findByRoleName("MEMBER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));

        // Fetch the chatroom
        Chatroom chatroom = chatroomRepository.findById(chatroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Chatroom not found with id: " + chatroomId));

        // Fetch the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Create and save the GroupMember object
        GroupMember groupMember = new GroupMember(chatroom, user, defaultRole);
        return groupMemberRepository.save(groupMember);
    }

    // Kiểm tra quyền của người dùng trong phòng
    public boolean isUserAdminOrOwner(Long userId, Long chatroomId) {
        return groupMemberRepository.findByChannelIdAndUserId(chatroomId, userId)
                .stream() // Chuyển sang Stream
                .map(groupMember -> groupMember.getRole().getRoleName()) // Lấy roleName
                .anyMatch(roleName -> "ADMIN".equals(roleName) || "OWNER".equals(roleName)); // Kiểm tra vai trò
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
