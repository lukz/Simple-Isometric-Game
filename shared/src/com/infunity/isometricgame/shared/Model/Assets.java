package com.infunity.isometricgame.shared.Model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class Assets {

    // Textures
    public final String PlayerTex = "graphics/character.png";
    public final String CoinTex = "graphics/coin.png";

    // Particles
    public final String CoinPrt = "particles/coin.p";

    private AssetManager manager;

    public Assets() {
        manager = new AssetManager();

        enqueueAssets();
        //manager.finishLoading();
    }

    public void enqueueAssets() {
        // Textures
        manager.load(PlayerTex, Texture.class);
        manager.load(CoinTex, Texture.class);

        // Particles
        manager.load(CoinPrt, ParticleEffect.class);
    }

    public boolean update() {
        return manager.update();
    }

    public boolean isLoaded(String fileName) {
        return manager.isLoaded(fileName);
    }

    public float getProgress() {
        return manager.getProgress();
    }

    public <T> T get (String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }

    public void dispose() {
        manager.dispose();
    }

}
