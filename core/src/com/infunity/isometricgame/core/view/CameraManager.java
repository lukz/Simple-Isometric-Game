package com.infunity.isometricgame.core.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.shared.model.entities.Player;
import com.infunity.isometricgame.shared.model.maps.Map;

public class CameraManager {

    private OrthographicCamera camera;
    private Player player;
    private Map map;

    private Vector2 newCamPos = new Vector2(0, 0);

    private boolean continuousMovement = true;

    public CameraManager(Map map) {
        this.player = map.getPlayer();
        this.map = map;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, IsometricGame.TARGET_WIDTH,
                IsometricGame.TARGET_HEIGHT);
    }

    public void update() {
        newCamPos.x = MathUtils.clamp(player.getPositionX(),
                0 + (camera.viewportWidth * camera.zoom) / 2,
                map.getTileWidth() * map.getWidth() - map.getTileWidth() / 2 - (camera.viewportWidth * camera.zoom) / 2);

        newCamPos.y = MathUtils.clamp(map.getPlayer().getPositionY(),
                map.getTileHeight() / 2 + (camera.viewportHeight * camera.zoom) / 2,
                map.getTileHeight() * map.getHeight() / 2 - (camera.viewportHeight * camera.zoom) / 2);


        if(continuousMovement || playerOutsideCam()) {
            camera.position.x = (int)newCamPos.x;
            camera.position.y = (int)newCamPos.y;
        }

        camera.update();
    }

    public boolean playerOutsideCam() {
        if(player.getPositionX() + player.getWidth() / 2 > camera.position.x + camera.viewportWidth / 2 ||
                player.getPositionX() - player.getWidth() / 2 < camera.position.x - camera.viewportWidth / 2)
            return true;

        if(player.getPositionY() + player.getHeight() / 2 > camera.position.y + camera.viewportHeight / 2 ||
                player.getPositionY() - player.getHeight() / 2 < camera.position.y - camera.viewportHeight / 2)
            return true;

        return false;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
