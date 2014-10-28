package com.infunity.isometricgame.core.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.core.utils.SaveGame;
import com.infunity.isometricgame.shared.model.GameWorld;

public class EscapeWindow extends Window {

    private GameWorld world;
    private WorldRenderer renderer;

    public EscapeWindow(Skin skin, Stage stage, GameWorld gameWorld, WorldRenderer worldRenderer) {
        super("", skin);

        this.world = gameWorld;
        this.renderer = worldRenderer;

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        TextButton saveExitButton = new TextButton("Save and exit", skin);
        saveExitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SaveGame.saveGame(world.getMap());
                Gdx.app.exit();
            }
        });

        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                world.setGameState(GameWorld.GameState.GAME_STARTED);
                setVisible(false);
            }
        });

        TextButton freeCameraButton = new TextButton("Free camera movement", skin, "toggle");
        freeCameraButton.setChecked(renderer.getCameraManager().isContinuousMovement());
        freeCameraButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(((TextButton)actor).isChecked()) {
                    renderer.getCameraManager().setContinuousMovement(true);
                } else {
                    renderer.getCameraManager().setContinuousMovement(false);
                }
            }
        });

        setMovable(false);

        // Make table for buttons to put them all in one column
        Table buttonTable = new Table();
        buttonTable.add(exitButton).pad(5);
        buttonTable.add(saveExitButton).pad(5);
        buttonTable.add(resumeButton).pad(5);

        // Add everything to window
        add(freeCameraButton).row();

        add("Do you want to exit game?").padTop(10);;
        row();
        add(buttonTable);
        pack();


        setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));
    }


}
