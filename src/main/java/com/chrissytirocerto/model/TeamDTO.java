package com.chrissytirocerto.model;

public class TeamDTO {

    private int id;
    private String name;

    public TeamDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}