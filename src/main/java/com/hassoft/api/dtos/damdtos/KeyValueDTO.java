package com.hassoft.api.dtos.damdtos;

public class KeyValueDTO {

    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "KeyValueDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
