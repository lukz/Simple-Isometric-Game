package com.infunity.isometricgame.Model.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.infunity.isometricgame.Model.Box2DWorld;
import com.infunity.isometricgame.Model.Maps.Map;
import com.infunity.isometricgame.Model.PhysicsObject;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class Player extends MovableEntity implements PhysicsObject {

    private Body body;

    // Used to store input keys
    private Vector2 direction;

    // Used to calculate output velocity
    private Vector2 velocity;

    public Player(float speed, float rotation, float x, float y, float width, float height, Box2DWorld box2DWorld) {
        super(speed, rotation, x, y, width, height);

        this.body = box2DWorld.createRectangle(x, y, width, height, BodyDef.BodyType.DynamicBody, true);

        this.direction = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
    }

    @Override
    public void update(float delta) {
        velocity.set(direction).nor().scl(speed * delta);

        body.setLinearVelocity(velocity);
    }

    @Override
    public void draw(SpriteBatch batch) {

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

    public Vector2 getDirection() {
        return direction;
    }

    @Override
    public void dispose() {

    }
}
