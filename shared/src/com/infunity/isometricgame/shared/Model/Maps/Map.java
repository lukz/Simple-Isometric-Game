package com.infunity.isometricgame.shared.model.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.infunity.isometricgame.shared.intefaces.EffectsInterface;
import com.infunity.isometricgame.shared.model.Box2DWorld;
import com.infunity.isometricgame.shared.model.entities.Player;
import com.infunity.isometricgame.shared.model.EntityManager;
import com.infunity.isometricgame.shared.model.PhysicsObject;

/**
 * Created by Lukasz on 2014-10-12.
 */
public abstract class Map implements ContactListener {

    protected EntityManager entMan;

    protected Box2DWorld box2dworld;
    protected Player player;
    protected TiledMap tileMap;

    protected EffectsInterface effectManager;

    protected int tileWidth;
    protected int tileHeight;
    protected int width;
    protected int height;

    // Time counter
    protected float gameTime = 0;

    protected Map(Box2DWorld box2dworld, EffectsInterface effectManager) {
        this.box2dworld = box2dworld;
        this.effectManager = effectManager;
        this.entMan = new EntityManager(this);

        this.player = new Player(100, 0, 0, 0, 80, 40, 100, 50, box2dworld);

        // Pass all collisions through this class
        box2dworld.getWorld().setContactListener(this);
    }

    public void update(float delta) {
        gameTime += delta;

        entMan.update(delta);
        player.update(delta);
    }

    public void resetGame() {
        entMan.reset();
    }

    @Override
    public void beginContact(Contact contact) {
        Object ent1 = contact.getFixtureA().getBody().getUserData();
        Object ent2 = contact.getFixtureB().getBody().getUserData();

        if(!(ent1 instanceof PhysicsObject) || !(ent2 instanceof PhysicsObject)) {
            return;
        }

        PhysicsObject physo1 = (PhysicsObject)ent1;
        PhysicsObject physo2 = (PhysicsObject)ent2;

        physo1.handleBeginContact(physo2, this);
        physo2.handleBeginContact(physo1, this);
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    public Player getPlayer() {
        return player;
    }

    public TiledMap getTileMap() {
        return tileMap;
    }

    public EffectsInterface getEffectManager() {
        return effectManager;
    }

    public EntityManager getEntMan() {
        return entMan;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getGameTime() {
        return gameTime;
    }

    public void dispose() {
        entMan.dispose();
        tileMap.dispose();
    };
}
