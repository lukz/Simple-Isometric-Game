package com.infunity.isometricgame.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.core.model.PlayerNavigator;
import com.infunity.isometricgame.core.utils.SaveGame;
import com.infunity.isometricgame.core.view.WorldRenderer;
import com.infunity.isometricgame.shared.model.maps.Map;
import com.infunity.isometricgame.shared.utils.MapDescriptor;

public class PlayerInputHandler implements InputProcessor {

    private Map map;
    private WorldRenderer renderer;

    private PlayerNavigator playerNav;

    private IsometricGame game;

    public PlayerInputHandler(Map map, WorldRenderer renderer, PlayerNavigator playerNav, IsometricGame game) {
        this.map = map;
        this.renderer = renderer;

        this.playerNav = playerNav;
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.LEFT:
                playerNav.stopNavigation();
                map.getPlayer().getDirection().x = -1;
                return true;
            case Input.Keys.RIGHT:
                playerNav.stopNavigation();
                map.getPlayer().getDirection().x = 1;
                return true;
            case Input.Keys.UP:
                playerNav.stopNavigation();
                map.getPlayer().getDirection().y = 1;
                return true;
            case Input.Keys.DOWN:
                playerNav.stopNavigation();
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

            case Input.Keys.S:
                SaveGame.saveGame(map);
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

    private Vector3 tempVec3 = new Vector3();
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Translate screen coordinates to world coordinates
        tempVec3.set(screenX, screenY, 0);
        tempVec3 = renderer.getCameraManager().getCamera().unproject(tempVec3);

        playerNav.navigatePlayerTo(tempVec3.x, tempVec3.y);

        return true;
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
