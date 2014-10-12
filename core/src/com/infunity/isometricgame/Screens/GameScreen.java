package com.infunity.isometricgame.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.infunity.isometricgame.IsometricGame;
import com.infunity.isometricgame.Model.GameWorld;
import com.infunity.isometricgame.Model.Maps.Map;
import com.infunity.isometricgame.Model.Maps.TestMap;
import com.infunity.isometricgame.Utils.Box2DWorld;
import com.infunity.isometricgame.View.WorldRenderer;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class GameScreen implements Screen {

    private IsometricGame game;

    private GameWorld world; // Logic
    private WorldRenderer render; // Renderer
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

        box2dworld = new Box2DWorld(new Vector2(0, -10));

        map = new TestMap(box2dworld);
        world = new GameWorld(box2dworld, map);
        render = new WorldRenderer(world);
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
            world.update(TIMESTEP);
            box2dworld.update(TIMESTEP);

            fixedTimestepAccumulator -= TIMESTEP;
        }

		/*
		 * Render
		 */
        render.render(delta);
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
