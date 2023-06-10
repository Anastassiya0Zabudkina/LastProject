package com.example.last;

public class ScheduleItem {
    private String title;
    private String time;
    private String location;
    private String date;

    public ScheduleItem(String title, String time, String location, String date) {
        this.title = title;
        this.time = time;
        this.location = location;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public ScheduleItem(String title, String time, String location) {
        this.title = title;
        this.time = time;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

