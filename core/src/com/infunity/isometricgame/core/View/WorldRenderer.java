package com.infunity.isometricgame.core.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.shared.Model.GameWorld;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class WorldRenderer {

    private GameWorld world;
    private OrthographicCamera cam;

    private IsometricStaggeredTiledMapRenderer tiledMapRenderer;

    public WorldRenderer(GameWorld world) {
        this.world = world;

        cam = new OrthographicCamera();
        cam.setToOrtho(false, IsometricGame.TARGET_WIDTH,
                IsometricGame.TARGET_HEIGHT);

        cam.position.x = IsometricGame.TARGET_WIDTH / 2;
        cam.position.y = IsometricGame.TARGET_HEIGHT / 2;

        cam.update();

        tiledMapRenderer = new IsometricStaggeredTiledMapRenderer(world.getMap().getTileMap());
        tiledMapRenderer.setView(cam);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.render();

        // Box2D debug rendering
        if (IsometricGame.DEBUG) {
            world.getBox2dworld().render(cam);
        }
    }

    public void resize(int width, int height) {

    }

    public void dispose() {
    }
}
