package com.infunity.isometricgame.core.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.core.View.EntityRenderers.CoinsRenderer;
import com.infunity.isometricgame.core.View.EntityRenderers.PlayerRenderer;
import com.infunity.isometricgame.shared.Model.Entities.Coin;
import com.infunity.isometricgame.shared.Model.GameWorld;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class WorldRenderer {

    private GameWorld world;
    private OrthographicCamera cam;

    private SpriteBatch batch;

    // Renderers
    private PlayerRenderer playerRenderer;
    private CoinsRenderer coinsRenderer;
    private IsometricStaggeredTiledMapRenderer tiledMapRenderer;

    private EffectManager effectManager;

    public WorldRenderer(GameWorld world) {
        this.world = world;

        batch = new SpriteBatch();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, IsometricGame.TARGET_WIDTH,
                IsometricGame.TARGET_HEIGHT);
        batch.setProjectionMatrix(cam.combined);

        tiledMapRenderer = new IsometricStaggeredTiledMapRenderer(world.getMap().getTileMap());
        tiledMapRenderer.setView(cam);

        playerRenderer = new PlayerRenderer(world.getMap().getPlayer());
        coinsRenderer = new CoinsRenderer();

        effectManager = (EffectManager)world.getMap().getEffectManager();
    }

    public void render(float delta) {
        // Update camera
        cam.position.x = world.getMap().getPlayer().getPositionX();
        cam.position.y = world.getMap().getPlayer().getPositionY();
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        tiledMapRenderer.setView(cam);

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

        batch.end();

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
