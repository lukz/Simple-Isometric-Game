package com.infunity.isometricgame.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.core.utils.PlayerScore;

import java.util.Comparator;

/**
 * Finish game screen containing list of highscores
 */
public class FinishScreen implements Screen {

    private IsometricGame game;
    private float gameTime = 0;

    private Skin skin;

    private Stage stage;
    private Table rootTable;

    private Preferences prefs;

    private float timer = 0;

    private boolean nameEntered = false;

    public FinishScreen(IsometricGame game, float gameTime) {
        this.game = game;
        this.gameTime = gameTime;

        // Get skin from asset manager
        skin = IsometricGame.assets.get(IsometricGame.assets.DefaultSkin, Skin.class);

        // Create Stage
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        // Get preferences
        prefs = Gdx.app.getPreferences("isometricgame_prefs");
        String playerName = prefs.getString("playerName", null);

        rootTable = new Table();
        rootTable.setFillParent(true);

        if(playerName == null) {
            // Create input window
            Window playerNameWindow = new Window("Player Name", skin);
            playerNameWindow.setModal(true);
            playerNameWindow.setMovable(false);

            // Create input field
            final TextField nameTextField = new TextField("", skin);

            // Create submit button
            TextButton submitButton = new TextButton("OK", skin);
            submitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(!nameTextField.getText().equals("")) {
                        // Save player name for later reuse
                        prefs.putString("playerName", nameTextField.getText());
                        prefs.flush();
                        // Clear root table
                        rootTable.clear();

                        showHighscores();
                    }
                }
            });

            // Add everything to window
            playerNameWindow.add("Enter your name:").padRight(5);
            playerNameWindow.add(nameTextField).row();
            playerNameWindow.add(submitButton);

            rootTable.add(playerNameWindow);
        } else {
            showHighscores();
        }

        stage.addActor(rootTable);
    }

    public void showHighscores() {
        nameEntered = true;
        Array<PlayerScore> highscores;// = new ArrayMap<String, Float>();

        String playerName = prefs.getString("playerName", null);

        Json jsonReader = new Json();

        // Read higscores from prefs parsing json
        String highscoresJson = prefs.getString("highscores", null);

        if(highscoresJson != null) {
            highscores = jsonReader.fromJson(Array.class, highscoresJson);
        } else {
            highscores = new Array<PlayerScore>();
        }

        // add new record to highscores
        highscores.add(new PlayerScore(playerName, gameTime));
        prefs.putString("highscores", jsonReader.toJson(highscores));
        prefs.flush();

        highscores.sort(new Comparator<PlayerScore>() {
            @Override
            public int compare(PlayerScore o1, PlayerScore o2) {
                if(o1.getTime() < o2.getTime()) return -1;
                if(o1.getTime() > o2.getTime()) return 1;
                return 0;
            }
        });

        // Create highscores strings array
        Array<String> highscoresStrings = new Array<String>();
        for(PlayerScore playerScore : highscores) {
            highscoresStrings.add(playerScore.getName() + ": " + ((int)playerScore.getTime()) + " sec");
        }

        List<String> highscoresList = new List<String>(skin);
        highscoresList.setItems(highscoresStrings);
        highscoresList.pack();

        rootTable.add(highscoresList);
    }

    @Override
    public void render(float delta) {
        if(nameEntered) {
            timer += delta;
        }
        if(timer > 5) {
            game.setScreen(new MainMenuScreen(game));
        }


        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
