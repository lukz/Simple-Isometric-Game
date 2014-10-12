package com.infunity.isometricgame.Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.infunity.isometricgame.Model.Entities.Entity;
import com.infunity.isometricgame.Model.Maps.Map;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class EntityManager {

    private Array<Entity> entities;
    private Map map;

    public EntityManager(Map map) {
        this.map = map;
        this.entities = new Array<Entity>();
    }

    public void update(float delta) {
        for (Entity entity : entities) {
            entity.update(delta);
        }
    }

    public void draw(SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.draw(batch);
        }
    }

    public void reset() {
        entities.clear();
    }

    public void dispose() {
        for (Entity entity : entities) {
            entity.dispose();
        }
    }

}
