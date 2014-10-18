package com.infunity.isometricgame.core.View.EntityRenderers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.shared.Model.Entities.Player;

/**
 * Created by Lukasz on 2014-10-15.
 */
public class PlayerRenderer {

    private Player player;
    private Sprite spr;

    public PlayerRenderer(Player player) {
        this.player = player;
        this.spr = new Sprite(IsometricGame.assets.get(IsometricGame.assets.PlayerTex, Texture.class));
    }

    public void render(SpriteBatch batch) {
        spr.setPosition(player.getPositionX() - player.getSpriteBoundingWidth() / 2,
                player.getPositionY() - player.getSpriteBoundingHeight() / 2);
        spr.draw(batch);
    }
}
