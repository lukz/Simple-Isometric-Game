package com.infunity.isometricgame.core.screens;

import com.badlogic.gdx.Screen;
import com.infunity.isometricgame.core.IsometricGame;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class LoadingScreen implements Screen {

    private IsometricGame game;

    public LoadingScreen(IsometricGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        // If assets loaded go to MainMenu
        if(game.assets.update()) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
