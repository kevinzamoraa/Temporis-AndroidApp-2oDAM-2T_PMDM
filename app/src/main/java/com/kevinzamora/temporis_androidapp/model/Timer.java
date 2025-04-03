package com.kevinzamora.temporis_androidapp.model;

import com.google.firebase.Timestamp;

public class Timer {
    private String id;
    private String title;
    private String description;
    private long duration;
    private boolean isActive;
    private Timestamp createdAt;

    // Constructor con valores por defecto
    public Timer() {
        this.id = "";
        this.title = "";
        this.description = "";
        this.duration = 0;
        this.isActive = false;
        this.createdAt = Timestamp.now();
    }

    // Constructor con todos los parámetros
    public Timer(String id, String title, String description, long duration, boolean isActive, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public long getDuration() { return duration; }
    public boolean isActive() { return isActive; }
    public Timestamp getCreatedAt() { return createdAt; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setDuration(long duration) { this.duration = duration; }
    public void setActive(boolean active) { this.isActive = active; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
