package com.infunity.isometricgame.shared.model.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.infunity.isometricgame.shared.model.Box2DWorld;
import com.infunity.isometricgame.shared.model.maps.Map;
import com.infunity.isometricgame.shared.model.PhysicsObject;

/**
 * Created by Lukasz on 2014-10-15.
 */
public class Coin extends Entity implements PhysicsObject {

    public static final int COIN_WIDTH = 40;
    public static final int COIN_HEIGHT = 40;

    private boolean flagForDelete = false;
    private Body body;

    public Coin(float x, float y, Box2DWorld box2DWorld) {
        super(x, y, COIN_WIDTH, COIN_HEIGHT);

        this.body = box2DWorld.createRectangle(x, y, width, height, BodyDef.BodyType.StaticBody, true, true);
        this.body.setUserData(this);
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
        return flagForDelete;
    }

    @Override
    public void setFlagForDelete(boolean flag) {
        flagForDelete = flag;
    }

    @Override
    public void dispose() {

    }
}
