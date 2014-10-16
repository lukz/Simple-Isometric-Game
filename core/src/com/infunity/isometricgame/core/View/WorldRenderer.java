package com.infunity.isometricgame.core.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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

    private CameraManager cameraManager;
    private SpriteBatch batch;

    // Renderers
    private PlayerRenderer playerRenderer;
    private CoinsRenderer coinsRenderer;
    private IsometricStaggeredTiledMapRenderer tiledMapRenderer;

    private EffectManager effectManager;

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

        batch.end();

        // Box2D debug rendering
        if (IsometricGame.DEBUG) {
            world.getBox2dworld().render(cameraManager.getCamera());
        }
    }

    public void resize(int width, int height) {

    }

    public void dispose() {
    }
}
