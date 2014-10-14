package com.infunity.isometricgame.shared.Model.Maps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.infunity.isometricgame.shared.Model.Box2DWorld;
import com.infunity.isometricgame.shared.Model.Entities.Player;
import com.infunity.isometricgame.shared.Model.EntityManager;
import com.infunity.isometricgame.shared.Model.PhysicsObject;

/**
 * Created by Lukasz on 2014-10-12.
 */
public abstract class Map implements ContactListener {

    protected EntityManager entMan;

    protected Box2DWorld box2dworld;
    protected Player player;

    protected TiledMap tileMap;

    protected Map(Box2DWorld box2dworld) {
        this.box2dworld = box2dworld;
        this.entMan = new EntityManager(this);

        this.player = new Player(100, 0, 100, 100, 32, 32, box2dworld);
    }

    public void update(float delta) {
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

    public void dispose() {
        entMan.dispose();
    };

    public Player getPlayer() {
        return player;
    }

    public TiledMap getTileMap() {
        return tileMap;
    }
}
