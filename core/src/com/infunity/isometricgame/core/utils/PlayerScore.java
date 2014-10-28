package com.infunity.isometricgame.core.utils;

/**
 * Small class used to store player scores in file
 */
public class PlayerScore {
    private String name;
    private float time;

    /**
     * Empty constructor for serialization
     */
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
