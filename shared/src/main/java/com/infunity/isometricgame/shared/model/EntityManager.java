package com.infunity.isometricgame.shared.model;

import com.badlogic.gdx.utils.Array;
import com.infunity.isometricgame.shared.model.entities.Coin;
import com.infunity.isometricgame.shared.model.entities.Entity;
import com.infunity.isometricgame.shared.model.maps.Map;

/**
 * Entity manager that handles entities state updates
 */
public class EntityManager {

    private Array<Coin> coinEntities;
    private Map map;

    public EntityManager(Map map) {
        this.map = map;
        this.coinEntities = new Array<Coin>();
    }

    public void update(float delta) {
        for (Entity entity : coinEntities) {
            entity.update(delta);
        }
    }

    public void reset() {
        coinEntities.clear();
    }

    public void addCoin(Coin coin) {
        coinEntities.add(coin);
    }

    public Array<Coin> getCoinEntities() {
        return coinEntities;
    }

    public void removeCoin(Coin coin) {
        coinEntities.removeValue(coin, true);
        coin.setFlagForDelete(true);
    }

    public int getCoinsLeft() {
        return coinEntities.size;
    }

    public void dispose() {
        for (Entity entity : coinEntities) {
            entity.dispose();
        }
    }
}
