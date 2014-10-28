package com.infunity.isometricgame.core.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Class that setup in-game camera SelectBox
 */
public class CameraSelectBox extends SelectBox {

    public CameraSelectBox(Skin skin, final WorldRenderer renderer) {
        super(skin);

        setItems(new String("Character moves"), new String("Map moves"));
        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (CameraSelectBox.this.getSelectedIndex() == 0) {
                    renderer.getCameraManager().setContinuousMovement(true);
                } else {
                    renderer.getCameraManager().setContinuousMovement(false);
                }
            }
        });

        if(renderer.getCameraManager().isContinuousMovement()) {
            setSelectedIndex(0);
        } else {
            setSelectedIndex(1);
        }

        pack();
    }
}
