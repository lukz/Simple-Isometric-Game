package com.infunity.isometricgame.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.infunity.isometricgame.core.input.PlayerInputHandler;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.core.view.EffectManager;
import com.infunity.isometricgame.core.view.ParticleManager;
import com.infunity.isometricgame.shared.model.GameWorld;
import com.infunity.isometricgame.shared.model.maps.Map;
import com.infunity.isometricgame.shared.model.maps.TestMap;
import com.infunity.isometricgame.shared.model.Box2DWorld;
import com.infunity.isometricgame.core.view.WorldRenderer;

public class GameScreen implements Screen {

    private IsometricGame game;

    /** Logic */
    private GameWorld world;

    /** Renderer */
    private WorldRenderer render;
    private Box2DWorld box2dworld;
    private Map map;

    /*
	 * Things for Fixed Timestep - look into render for implementation and docs
	 */
    private float fixedTimestepAccumulator = 0f;
    private final float MAX_ACCUMULATED_TIME = 1.0f;
    private final float TIMESTEP = 1/60f;

    public GameScreen(IsometricGame game) {
        this.game = game;

        box2dworld = new Box2DWorld(new Vector2(0, 0));

        map = new TestMap(box2dworld, new EffectManager(new ParticleManager()));
        world = new GameWorld(box2dworld, map);
        render = new WorldRenderer(world);

        Gdx.input.setInputProcessor(new PlayerInputHandler(map));
    }

    @Override
    public void render(float delta) {
        /*
		 * Implementation of fixed timestep
		 * docs:
		 * - http://gafferongames.com/game-physics/fix-your-timestep/
		 * - http://temporal.pr0.pl/devblog/download/arts/fixed_step/fixed_step.pdf
		 */

        fixedTimestepAccumulator += delta;
        if(fixedTimestepAccumulator > MAX_ACCUMULATED_TIME)
            fixedTimestepAccumulator = MAX_ACCUMULATED_TIME;

        while (fixedTimestepAccumulator >= TIMESTEP) {

			/*
			 * Update physics
			 */
            box2dworld.update(TIMESTEP);
            world.update(TIMESTEP);

            fixedTimestepAccumulator -= TIMESTEP;
        }

		/*
		 * Render
		 */
        render.render(delta);

        if(world.getGameState() == GameWorld.GameState.GAME_FINISHED) {
            game.setScreen(new FinishScreen(game, world.getMap().getGameTime()));
        }
    }

    @Override
    public void resize(int width, int height) {
        render.resize(width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        world.dispose();
        render.dispose();
    }
}
