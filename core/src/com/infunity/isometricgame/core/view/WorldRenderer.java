package com.infunity.isometricgame.core.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricStaggeredTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.core.view.entityRenderers.CoinsRenderer;
import com.infunity.isometricgame.core.view.entityRenderers.PlayerRenderer;
import com.infunity.isometricgame.shared.model.entities.Coin;
import com.infunity.isometricgame.shared.model.GameWorld;
import com.infunity.isometricgame.shared.model.entities.Player;

public class WorldRenderer {

    private GameWorld world;

    private CameraManager cameraManager;
    private SpriteBatch batch;

    // Map layers
    private TiledMapTileLayer groundLayer;
    private TiledMapTileLayer obstacleLayer;

    /** Array containing all players that will be rendered over or before obstacles */
    private Array<Player> playersToRender;

    // Renderers
    private PlayerRenderer playerRenderer;
    private CoinsRenderer coinsRenderer;
    private ExtendedIsometricStaggeredTiledMapRenderer tiledMapRenderer;

    private EffectManager effectManager;

    private BitmapFont font;

    public WorldRenderer(GameWorld world) {
        this.world = world;

        batch = new SpriteBatch();

        cameraManager = new CameraManager(world.getMap());

        batch.setProjectionMatrix(cameraManager.getCamera().combined);

        tiledMapRenderer = new ExtendedIsometricStaggeredTiledMapRenderer(world.getMap().getTileMap(), batch);
        tiledMapRenderer.setView(cameraManager.getCamera());

        groundLayer = (TiledMapTileLayer)world.getMap().getTileMap().getLayers().get("ground");
        obstacleLayer = (TiledMapTileLayer)world.getMap().getTileMap().getLayers().get("obstacles");

        playerRenderer = new PlayerRenderer();
        coinsRenderer = new CoinsRenderer();

        effectManager = (EffectManager)world.getMap().getEffectManager();

        font = IsometricGame.assets.get(IsometricGame.assets.DefaultFnt, BitmapFont.class);

        playersToRender = new Array<Player>();
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

        batch.begin();

        // Render map
        tiledMapRenderer.renderTileLayer(groundLayer);

        // Render entities
        for(Coin coin : world.getMap().getEntMan().getCoinEntities()) {
            coinsRenderer.render(batch, coin);
        }

        /**
         * Render obstacles and players
         */
        // Add players that would be rendered
        playersToRender.add(world.getMap().getPlayer());

        // For many players you should sort them from down to up (y axis) in playersToRender

        // Render obstacle tiles with check for every tile if player should be rendered before
        tiledMapRenderer.renderTileLayer(obstacleLayer, playerRenderer, playersToRender);

        // Render rest of players (below any obstacle)
        for(Player player : playersToRender) {
            playerRenderer.render(batch, player);
        }
        playersToRender.clear();

        effectManager.draw(batch);

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
