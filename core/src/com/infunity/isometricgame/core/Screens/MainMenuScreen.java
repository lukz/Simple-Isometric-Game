package com.infunity.isometricgame.core.Screens;

import com.badlogic.gdx.Screen;
import com.infunity.isometricgame.core.IsometricGame;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class MainMenuScreen implements Screen {

    private IsometricGame game;

    public MainMenuScreen(IsometricGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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
