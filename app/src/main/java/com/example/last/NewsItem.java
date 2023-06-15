package com.example.last;

public class NewsItem {
    private String title;
    private String description;

    public NewsItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

