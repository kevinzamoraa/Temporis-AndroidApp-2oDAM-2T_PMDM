package com.kevinzamora.temporis_androidapp.model;

import com.google.firebase.Timestamp;
import java.util.Objects;

public class Timer {
    private String id;
    private String name;
    private int duration; // en minutos
    private boolean isActive;
    private Timestamp createdAt;
    private String uid;

    public Timer() {

    }

    public Timer(String id, String name, int duration, boolean isActive, Timestamp createdAt, String uid) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isActive() {
        return isActive;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public String getUid() {
        return uid;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Timer)) return false;
        Timer timer = (Timer) o;
        return duration == timer.duration &&
                isActive == timer.isActive &&
                Objects.equals(id, timer.id) &&
                Objects.equals(name, timer.name) &&
                Objects.equals(createdAt, timer.createdAt) &&
                Objects.equals(uid, timer.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, isActive, createdAt, uid);
    }

}
