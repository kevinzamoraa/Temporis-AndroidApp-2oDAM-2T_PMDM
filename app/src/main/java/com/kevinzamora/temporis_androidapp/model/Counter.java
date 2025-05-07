package com.kevinzamora.temporis_androidapp.model;

import java.util.HashMap;
import java.util.Map;

public class Counter {
    private String id;
    private String title;
    private String type;
    private int actualValue;
    private int dailyObjective;
    private boolean active;

    // Constructor con valores por defecto
    public Counter() {
        this.id = "";
        this.title = "";
        this.type = "";
        this.actualValue = 0;
        this.dailyObjective = 0;
        this.active = true;
    }

    // Constructor con todos los parámetros
    public Counter(String id, String title, String type, int actualValue, int dailyObjective, boolean active) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.actualValue = actualValue;
        this.dailyObjective = dailyObjective;
        this.active = active;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getType() { return type; }
    public int getActualValue() { return actualValue; }
    public int getDailyObjective() { return dailyObjective; }
    public boolean isActive() { return active; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setType(String type) { this.type = type; }
    public void setActualValue(int actualValue) { this.actualValue = actualValue; }
    public void setDailyObjective(int dailyObjective) { this.dailyObjective = dailyObjective; }
    public void setActive(boolean active) { this.active = active; }

    public boolean reachedGoal() {
        return actualValue >= dailyObjective;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("title", title);
        map.put("type", type);
        map.put("actualValue", actualValue);
        map.put("dailyObjective", dailyObjective);
        map.put("active", active);
        return map;
    }
}
