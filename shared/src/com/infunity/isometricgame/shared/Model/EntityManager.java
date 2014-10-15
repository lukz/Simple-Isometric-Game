package com.infunity.isometricgame.shared.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.infunity.isometricgame.shared.Model.Entities.Coin;
import com.infunity.isometricgame.shared.Model.Entities.Entity;
import com.infunity.isometricgame.shared.Model.Maps.Map;

/**
 * Created by Lukasz on 2014-10-12.
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

    public void dispose() {
        for (Entity entity : coinEntities) {
            entity.dispose();
        }
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
}
