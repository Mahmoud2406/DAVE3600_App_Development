package com.example.todolist.Modules;


import android.icu.util.Calendar;

import java.util.Date;

public class Task {

    private int id;
    private String title;
    private Boolean completed;
    private Integer order;
    private Date dueTo;

    public Task(int id, String title, Boolean completed, Integer order,Date dueTo) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.order = order;
        this.dueTo = dueTo;
    }

    public Date getDueTo() {
        return dueTo;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public Integer getOrder() {
        return order;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
