package com.example.last;

public class ScheduleItem {
    private String title;
    private String time;
    private String location;
    private String date;

    private String status;

    private boolean isEnrolled;

    public ScheduleItem(String title, String time, String location, String date) {
        this.title = title;
        this.time = time;
        this.location = location;
        this.date = date;
        this.status = "Не записан";
        this.isEnrolled = false;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(boolean enrolled) {
        isEnrolled = enrolled;
    }

}


