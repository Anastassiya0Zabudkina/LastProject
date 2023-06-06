package com.example.last;

public class HomeworkTask {
    private String title;
    private String dueDate;
    private String description;

    private String mark;

    public HomeworkTask(String title, String dueDate, String description, String Mark) {
        this.title = title;
        this.dueDate = dueDate;
        this.description = description;
        this.mark = mark;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getMark(){ return mark; }
}

