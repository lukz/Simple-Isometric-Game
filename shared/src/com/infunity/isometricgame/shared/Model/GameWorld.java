package com.infunity.isometricgame.shared.Model;

import com.badlogic.gdx.Gdx;
import com.infunity.isometricgame.shared.Model.Maps.Map;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class GameWorld {

    private Map map;
    private Box2DWorld box2dworld;

    public enum GameState {
        GAME_STARTED, GAME_FINISHED
    }

    private GameState gameState = GameState.GAME_STARTED;

    public GameWorld(Box2DWorld box2dworld, Map map) {
        this.box2dworld = box2dworld;
        this.map = map;

    }

    public void resetGame() {
        map.resetGame();
    }

    public void update(float delta) {
        map.update(delta);


        if(map.getEntMan().getCoinsLeft() == 0) {
            gameState = GameState.GAME_FINISHED;
        }
    }

    public Map getMap() {
        return map;
    }

    public Box2DWorld getBox2dworld() {
        return box2dworld;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void dispose() {
        map.dispose();
    }
}
