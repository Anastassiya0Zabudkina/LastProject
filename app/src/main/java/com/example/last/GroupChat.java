package com.example.last;

import java.util.List;

public class GroupChat {
    private String chatId;
    private String chatName;
    private String chatAvatar;
    private List<User> members;

    public GroupChat() {
        // Default constructor required for Firebase
    }

    public GroupChat(String chatId, String chatName, String chatAvatar, List<User> members) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.chatAvatar = chatAvatar;
        this.members = members;
    }

    public String getChatId() {
        return chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public String getChatAvatar() {
        return chatAvatar;
    }

    public List<User> getMembers() {
        return members;
    }
}

