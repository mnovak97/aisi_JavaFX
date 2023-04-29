package com.example.aisi.model;

public class Team {
    private int id;

    private String name;

    @Override
    public String toString() {
        return name;
    }

    public Team(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
