package com.infunity.isometricgame.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.core.utils.SaveGame;
import com.infunity.isometricgame.shared.utils.MapDescriptor;

/**
 * Load game screen with ability load and delete any save game from list
 */
public class LoadGameScreen implements Screen {

    private IsometricGame game;

    private Stage stage;
    private Table rootTable;

    private Array<MapDescriptor> saveGames;
    private List<String> saveGameList;

    public LoadGameScreen(IsometricGame game) {
        this.game = game;

        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Skin skin = IsometricGame.assets.get(IsometricGame.assets.DefaultSkin, Skin.class);

        rootTable = new Table();
        rootTable.setFillParent(true);

        // Get save games
        saveGames = SaveGame.getSaveGames();

        // Create saveGames strings array
        Array<String> saveGameStrings = new Array<String>();
        for(int i = 0; i < saveGames.size; i++) {
            saveGameStrings.add((i + 1) + ": " + saveGames.get(i).getCoinsPos().size + " coins left");
        }

        // Crate saveGames list
        saveGameList = new List<String>(skin);
        saveGameList.setItems(saveGameStrings);
        saveGameList.pack();

        // Create delete button
        TextButton deleteGameButton = new TextButton("Delete", skin);
        deleteGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveGame.deleteSaveGame(saveGameList.getSelectedIndex());
                game.setScreen(new LoadGameScreen(game));
            }
        });

        // Create load game button
        TextButton loadGameButton = new TextButton("Load", skin);
        loadGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveGame.loadGame(game, saveGames.get(saveGameList.getSelectedIndex()));
            }
        });

        // Create load game button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Table buttonsTable = new Table();
        buttonsTable.add(deleteGameButton);
        buttonsTable.add(loadGameButton);

        rootTable.add(saveGameList).row();
        rootTable.add(buttonsTable).row();
        rootTable.add(backButton).fillX();

        stage.addActor(rootTable);

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
