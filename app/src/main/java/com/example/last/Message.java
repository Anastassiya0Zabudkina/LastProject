package com.example.last;

public class Message {
    private String messageId;
    private String userId;
    private String content;

    private String sender;

    private String message;

    public Message() {
        // пустое поля для работы бд
    }

    public Message(String messageId, String userId, String content) {
        this.messageId = messageId;
        this.userId = userId;
        this.content = content;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Message(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}


