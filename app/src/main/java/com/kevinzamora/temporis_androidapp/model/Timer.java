package com.kevinzamora.temporis_androidapp.model;

import com.google.firebase.Timestamp;

public class Timer {
    private String id;
    private String name;
    private long duration;
    private boolean isActive;
    private Timestamp createdAt;

    public Timer() {
        this.id = "";
        this.name = "";
        this.duration = 0;
        this.isActive = false;
        this.createdAt = Timestamp.now();
    }

    public Timer(String id, String name, long duration, boolean isActive, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.isActive = isActive;
        this.createdAt = createdAt != null ? createdAt : Timestamp.now();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public long getDuration() { return duration; }
    public boolean isActive() { return isActive; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDuration(long duration) { this.duration = duration; }
    public void setActive(boolean active) { this.isActive = active; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}