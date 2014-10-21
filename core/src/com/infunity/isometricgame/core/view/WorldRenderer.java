package com.infunity.isometricgame.core.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.core.view.entityRenderers.CoinsRenderer;
import com.infunity.isometricgame.core.view.entityRenderers.PlayerRenderer;
import com.infunity.isometricgame.shared.model.entities.Coin;
import com.infunity.isometricgame.shared.model.GameWorld;

public class WorldRenderer {

    private GameWorld world;

    private CameraManager cameraManager;
    private SpriteBatch batch;

    // Renderers
    private PlayerRenderer playerRenderer;
    private CoinsRenderer coinsRenderer;
    private IsometricStaggeredTiledMapRenderer tiledMapRenderer;

    private EffectManager effectManager;

    private BitmapFont font;

    public WorldRenderer(GameWorld world) {
        this.world = world;

        batch = new SpriteBatch();

        cameraManager = new CameraManager(world.getMap());

        batch.setProjectionMatrix(cameraManager.getCamera().combined);

        tiledMapRenderer = new IsometricStaggeredTiledMapRenderer(world.getMap().getTileMap());
        tiledMapRenderer.setView(cameraManager.getCamera());

        playerRenderer = new PlayerRenderer(world.getMap().getPlayer());
        coinsRenderer = new CoinsRenderer();

        effectManager = (EffectManager)world.getMap().getEffectManager();

        font = IsometricGame.assets.get(IsometricGame.assets.DefaultFnt, BitmapFont.class);
    }


    public void render(float delta) {
        // Update camera
        cameraManager.update();
        batch.setProjectionMatrix(cameraManager.getCamera().combined);
        tiledMapRenderer.setView(cameraManager.getCamera());

        // Update particles
        effectManager.update(delta);

        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render map
        tiledMapRenderer.render();

        batch.begin();

        // Render entities
        for(Coin coin : world.getMap().getEntMan().getCoinEntities()) {
            coinsRenderer.render(batch, coin);
        }
        effectManager.draw(batch);
        playerRenderer.render(batch);

        // Draw game time
        font.draw(batch, String.valueOf((int)world.getMap().getGameTime()) + " sec",
                cameraManager.getCamera().position.x - cameraManager.getCamera().viewportWidth / 2 + 20,
                cameraManager.getCamera().position.y + cameraManager.getCamera().viewportHeight / 2 - 20);

        batch.end();

        // Box2D debug rendering
        if (IsometricGame.DEBUG) {
            world.getBox2dworld().render(cameraManager.getCamera());
        }
    }

    public void resize(int width, int height) {

    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void dispose() {
    }
}