package com.infunity.isometricgame.shared.model.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.infunity.isometricgame.shared.intefaces.EffectsInterface;
import com.infunity.isometricgame.shared.model.Box2DWorld;
import com.infunity.isometricgame.shared.model.entities.Player;
import com.infunity.isometricgame.shared.model.EntityManager;
import com.infunity.isometricgame.shared.model.PhysicsObject;

public abstract class Map implements PhysicsObject {

    protected EntityManager entMan;

    protected Box2DWorld box2dworld;
    protected Player player;
    protected TiledMap tileMap;

    protected EffectsInterface effectManager;

    protected int tileWidth;
    protected int tileHeight;
    protected int width;
    protected int height;

    /** Time counter */
    protected float gameTime = 0;

    protected Map(Box2DWorld box2dworld, EffectsInterface effectManager) {
        this.box2dworld = box2dworld;
        this.effectManager = effectManager;
        this.entMan = new EntityManager(this);

        this.player = new Player(100, 0, 0, 0, 80, 40, 100, 50, box2dworld);


    }

    public void update(float delta) {
        gameTime += delta;

        entMan.update(delta);
        player.update(delta);
    }

    @Override
    public void handleBeginContact(PhysicsObject psycho2, Map map) {

    }

    @Override
    public Body getBody() {
        // Not used
        return null;
    }

    @Override
    public boolean getFlagForDelete() {
        // Not used
        return false;
    }

    @Override
    public void setFlagForDelete(boolean flag) {
        // Not used
    }

    public void resetGame() {
        entMan.reset();
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
