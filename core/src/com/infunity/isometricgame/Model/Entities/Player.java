package com.infunity.isometricgame.Model.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.infunity.isometricgame.Model.Maps.Map;
import com.infunity.isometricgame.Model.PhysicsObject;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class Player extends MovableEntity implements PhysicsObject {

    private Body body;

    public Player(float speed, float rotation, float x, float y, float width, float height) {
        super(speed, rotation, x, y, width, height);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void handleBeginContact(PhysicsObject psycho2, Map map) {

    }

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public boolean getFlagForDelete() {
        return false;
    }

    @Override
    public void setFlagForDelete(boolean flag) {
        // Not used here!
    }

    @Override
    public void dispose() {

    }
}
