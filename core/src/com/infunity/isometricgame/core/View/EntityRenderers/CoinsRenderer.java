package com.infunity.isometricgame.core.View.EntityRenderers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.shared.Model.Entities.Coin;
import com.infunity.isometricgame.shared.Model.Entities.Player;

/**
 * Created by Lukasz on 2014-10-15.
 */
public class CoinsRenderer {

    private Sprite spr;

    public CoinsRenderer() {
        this.spr = new Sprite(IsometricGame.assets.get(IsometricGame.assets.CoinTex, Texture.class));
    }

    public void render(SpriteBatch batch, Coin coin) {
        spr.setPosition(coin.getPositionX() - coin.getWidth() / 2,
                coin.getPositionY() - coin.getHeight() / 2);
        spr.draw(batch);
    }
}
