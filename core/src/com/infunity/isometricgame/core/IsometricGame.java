package com.infunity.isometricgame.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;
import com.infunity.isometricgame.shared.Model.Assets;
import com.infunity.isometricgame.core.Screens.LoadingScreen;

public class IsometricGame extends Game {

    public static final boolean DEBUG = false;
    public static final float TARGET_WIDTH = 1024;
    public static final float TARGET_HEIGHT = 576;

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
