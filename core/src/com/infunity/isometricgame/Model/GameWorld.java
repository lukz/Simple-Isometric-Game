package com.infunity.isometricgame.Model;

import com.badlogic.gdx.Gdx;
import com.infunity.isometricgame.Input.PlayerInputHandler;
import com.infunity.isometricgame.Model.Maps.Map;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class GameWorld {

    private Map map;
    private Box2DWorld box2dworld;

    public GameWorld(Box2DWorld box2dworld, Map map) {
        this.box2dworld = box2dworld;
        this.map = map;

        Gdx.input.setInputProcessor(new PlayerInputHandler(map));
    }

    public void resetGame() {
        map.resetGame();
    }

    public void update(float delta) {
        map.update(delta);
    }

    public Map getMap() {
        return map;
    }

    public void dispose() {
        map.dispose();
    }

    public Box2DWorld getBox2dworld() {
        return box2dworld;
    }
}
