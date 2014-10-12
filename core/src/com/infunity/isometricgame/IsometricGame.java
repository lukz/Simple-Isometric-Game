package com.infunity.isometricgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.infunity.isometricgame.Model.Assets;
import com.infunity.isometricgame.Screens.LoadingScreen;

public class IsometricGame extends Game {

    public static final boolean DEBUG = false;
    public static final float TARGET_WIDTH = 576;
    public static final float TARGET_HEIGHT = 1024;

    public static Assets assets;

    private FPSLogger log;


    public IsometricGame() {

    }

    @Override
	public void create () {
        assets = new Assets();
        log = new FPSLogger();

        this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {
        super.render();
        log.log();
	}

    @Override
    public void dispose() {
        assets.dispose();
        super.dispose();
    }
}
