package com.infunity.isometricgame.core.view.entityRenderers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.shared.model.entities.Player;

public class PlayerRenderer {

    private Player player;
    private Sprite spr;

    public PlayerRenderer() {
        this.spr = new Sprite(IsometricGame.assets.get(IsometricGame.assets.PlayerTex, Texture.class));
    }

    public void render(SpriteBatch batch, Player player) {
        spr.setPosition(player.getPositionX() - player.getSpriteBoundingWidth() / 2,
                player.getPositionY() - player.getSpriteBoundingHeight() / 2);
        spr.draw(batch);
    }
}
