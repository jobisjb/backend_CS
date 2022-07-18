package com.wywm.capstone_backend.services;

public class Trooper {
    private String name;
    private int num;
    private int percentage;

    public Trooper(String name, int num, int percentage) {
        this.name = name;
        this.num = num;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public int getPercentage() {
        return percentage;
    }
}
