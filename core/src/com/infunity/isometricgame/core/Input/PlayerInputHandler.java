package com.infunity.isometricgame.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.infunity.isometricgame.shared.model.maps.Map;

/**
 * Created by Lukasz on 2014-10-13.
 */
public class PlayerInputHandler implements InputProcessor {

    private Map map;

    public PlayerInputHandler(Map map) {
        this.map = map;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.LEFT:
                map.getPlayer().getDirection().x = -1;
                return true;
            case Input.Keys.RIGHT:
                map.getPlayer().getDirection().x = 1;
                return true;
            case Input.Keys.UP:
                map.getPlayer().getDirection().y = 1;
                return true;
            case Input.Keys.DOWN:
                map.getPlayer().getDirection().y = -1;
                return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch(keycode){
            case Input.Keys.LEFT:
                if(map.getPlayer().getDirection().x == -1)
                    map.getPlayer().getDirection().x = 0;
                return true;
            case Input.Keys.RIGHT:
                if(map.getPlayer().getDirection().x == 1)
                    map.getPlayer().getDirection().x = 0;
                return true;
            case Input.Keys.UP:
                if(map.getPlayer().getDirection().y == 1)
                    map.getPlayer().getDirection().y = 0;
                return true;
            case Input.Keys.DOWN:
                if(map.getPlayer().getDirection().y == -1)
                    map.getPlayer().getDirection().y = 0;
                return true;
        }

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
