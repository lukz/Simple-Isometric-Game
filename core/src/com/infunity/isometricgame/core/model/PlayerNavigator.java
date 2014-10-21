package com.infunity.isometricgame.core.model;

import com.badlogic.gdx.math.Vector2;
import com.infunity.isometricgame.shared.model.entities.Player;

public class PlayerNavigator {

    private Player player;

    private boolean coordinatesSet = false;
    private Vector2 coordinates;
    private Vector2 direction;
    private Vector2 startPos;
    private float navDistance = 0;

    private Vector2 tempVec2 = new Vector2(0, 0);

    public PlayerNavigator(Player player) {
        this.player = player;

        coordinates = new Vector2();
        direction = new Vector2();
        startPos =  new Vector2();
    }

    public void update() {
        if(!coordinatesSet)
            return;

        tempVec2.set(player.getPositionX(), player.getPositionY());

        if(tempVec2.dst(startPos) > navDistance) {
            stopNavigation();
        }
    }

    public void navigatePlayerTo(float x, float y) {
        coordinatesSet = true;
        player.setNavigated(true);

        coordinates.set(x, y);
        startPos.set(player.getPositionX(), player.getPositionY());

        tempVec2.set(startPos);
        direction.set(coordinates);
        direction.sub(tempVec2).nor();

        player.getDirection().set(direction);
        navDistance = startPos.dst(coordinates);
    }

    public void stopNavigation() {
        if(coordinatesSet) {
            coordinatesSet = false;
            player.getDirection().set(0, 0);

            player.setNavigated(false);
        }
    }
}
