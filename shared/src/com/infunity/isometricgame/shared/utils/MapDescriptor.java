package com.infunity.isometricgame.shared.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.infunity.isometricgame.shared.model.entities.Coin;
import com.infunity.isometricgame.shared.model.maps.Map;

public class MapDescriptor {

    private float gameTime = 0;

    private Array<Vector2> coinsPos;
    private Vector2 playerPos;

    private transient boolean descriptorSet = false;

    public void setMapDescriptor(Map map) {
        descriptorSet = true;

        // Set player pos
        playerPos = new Vector2(map.getPlayer().getPositionX(), map.getPlayer().getPositionY());

        // Save gameTime
        gameTime = map.getGameTime();

        // Save coins left
        coinsPos = new Array<Vector2>();
        for(Coin coin : map.getEntMan().getCoinEntities()) {
            coinsPos.add(new Vector2(coin.getPositionX(), coin.getPositionY()));
        }
    }

    public float getGameTime() {
        return gameTime;
    }

    public Array<Vector2> getCoinsPos() {
        return coinsPos;
    }

    public Vector2 getPlayerPos() {
        return playerPos;
    }

    public boolean isDescriptorSet() {
        return descriptorSet;
    }
}
