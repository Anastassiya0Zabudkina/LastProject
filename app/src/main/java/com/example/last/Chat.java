package com.example.last;

import java.util.List;

public class Chat {
    private String id;
    private String title;
    private String participants;

    public Chat(String chatId, String chatTitle, List<String> participants) {
        // пустое поле для работы бд
    }

    public Chat(String id, String title, String participants) {
        this.id = id;
        this.title = title;
        this.participants = participants;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getParticipants() {
        return participants;
    }


}

