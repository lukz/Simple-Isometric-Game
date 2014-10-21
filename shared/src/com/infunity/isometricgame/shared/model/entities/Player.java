package com.infunity.isometricgame.shared.model.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.infunity.isometricgame.shared.intefaces.EffectsInterface;
import com.infunity.isometricgame.shared.model.Box2DWorld;
import com.infunity.isometricgame.shared.model.maps.Map;
import com.infunity.isometricgame.shared.model.PhysicsObject;

public class Player extends MovableEntity implements PhysicsObject {

    private Body body;

    /** Used to store input keys */
    private Vector2 direction;

    /** Used to know if player is navigated by client */
    private boolean navigated = false;

    /** Used to calculate output velocity */
    private Vector2 velocity;

    private float spriteBoundingWidth = 0;
    private float spriteBoundingHeight = 0;

    public Player(float speed, float rotation, float x, float y, float boundingWidth, float boundingHeight,
                  float spriteBoundingWidth, float spriteBoundingHeight, Box2DWorld box2DWorld) {
        super(speed, rotation, x, y, boundingWidth, boundingHeight);

        this.body = box2DWorld.createPlayerBody(x, y, boundingWidth, boundingHeight, BodyDef.BodyType.DynamicBody, true);
        this.body.setUserData(this);

        this.direction = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);

        this.spriteBoundingWidth = spriteBoundingWidth;
        this.spriteBoundingHeight = spriteBoundingHeight;
    }

    @Override
    public void update(float delta) {
        // Get player position from Box2D world
        setPosition(body.getPosition().scl(Box2DWorld.BOX_TO_WORLD), 0);

        velocity.set(direction).nor().scl(speed * delta);

        body.setLinearVelocity(velocity);
    }

    @Override
    public void handleBeginContact(PhysicsObject psycho2, Map map) {
        if(psycho2 instanceof  Map) {
            // Stop navigation on collision
            if(navigated) {
                navigated = false;
                direction.set(0, 0);
            }
        }

        if(psycho2 instanceof Coin) {
            Coin coin = (Coin)psycho2;

            map.getEntMan().removeCoin(coin);
            map.getEffectManager().createEffect((int)coin.getPositionX(), (int)coin.getPositionY(),
                    EffectsInterface.Effect.COIN_EFFECT);
        }
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

    /** Sets player body position */
    public void transform(Vector2 newPos) {
        body.setTransform(newPos.x * Box2DWorld.WORLD_TO_BOX, newPos.y * Box2DWorld.WORLD_TO_BOX, 0);
    }

    public float getSpriteBoundingWidth() {
        return spriteBoundingWidth;
    }

    public float getSpriteBoundingHeight() {
        return spriteBoundingHeight;
    }

    public boolean isNavigated() {
        return navigated;
    }

    public void setNavigated(boolean navigated) {
        this.navigated = navigated;
    }

    @Override
    public void dispose() {

    }
}
