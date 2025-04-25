package com.kevinzamora.temporis_androidapp.model;

import java.util.HashMap;
import java.util.Map;

public class TimeRegister {
    private String id;
    private long date;
    private int duration;
    private String category;
    private String description;
    private String counterId;

    // Constructor con valores por defecto
    public TimeRegister() {
        this.id = "";
        this.date = 0L;
        this.duration = 0;
        this.category = "";
        this.description = "";
        this.counterId = null;
    }

    // Constructor con todos los parámetros
    public TimeRegister(String id, long date, int duration, String category, String description, String counterId) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.category = category;
        this.description = description;
        this.counterId = counterId;
    }

    // Getters
    public String getId() { return id; }
    public long getDate() { return date; }
    public int getDuration() { return duration; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getCounterId() { return counterId; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setDate(long date) { this.date = date; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setCounterId(String counterId) { this.counterId = counterId; }

    public boolean isValid() {
        return id != null && !id.isEmpty() &&
                date > 0L &&
                duration >= 0;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("date", date);
        map.put("duration", duration);
        map.put("category", category);
        map.put("description", description);

        if (counterId != null) {
            map.put("counterId", counterId);
        }

        return map;
    }
}
