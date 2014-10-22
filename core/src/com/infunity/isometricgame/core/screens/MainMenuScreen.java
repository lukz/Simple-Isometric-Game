package com.infunity.isometricgame.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.infunity.isometricgame.core.IsometricGame;

public class MainMenuScreen implements Screen {

    private IsometricGame game;
    private Stage stage;

    public MainMenuScreen(IsometricGame game) {
        this.game = game;

        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Skin skin = IsometricGame.assets.get(IsometricGame.assets.DefaultSkin, Skin.class);

        // Create New Game button
        TextButton newGameButton = new TextButton("New game", skin);
        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
            }
        });

        // Create Load Game button
        TextButton loadGameButton = new TextButton("Load game", skin);
        loadGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoadGameScreen(game));
            }
        });

        // Create Quit Game button
        TextButton quitGameButton = new TextButton("Quit game", skin);
        quitGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        newGameButton.setPosition(300, 300);
        loadGameButton.setPosition(300, 250);
        quitGameButton.setPosition(300, 200);

        stage.addActor(newGameButton);
        stage.addActor(loadGameButton);
        stage.addActor(quitGameButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
