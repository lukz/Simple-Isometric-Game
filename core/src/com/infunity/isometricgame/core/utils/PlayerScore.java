package com.infunity.isometricgame.core.utils;

public class PlayerScore {
    private String name;
    private float time;

    public PlayerScore() {

    };

    public PlayerScore(String name, float time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public float getTime() {
        return time;
    }
}
