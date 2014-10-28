package com.infunity.isometricgame.shared.model;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Simple asset manager
 */
public class Assets {

    // Textures
    public final String PlayerTex = "graphics/character.png";
    public final String CoinTex = "graphics/coin.png";

    // Particles
    public final String CoinPrt = "particles/coin.p";

    // Fonts
    public final String DefaultFnt = "skin/default.fnt";

    // Skinks
    public final String DefaultSkin = "skin/uiskin.json";

    // Sounds
    public final String CoinSound = "sounds/coin.ogg";
    public final String FinishSound = "sounds/finish.ogg";

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

        // Fonts
        manager.load(DefaultFnt, BitmapFont.class);

        // Skins
        manager.load(DefaultSkin, Skin.class);

        // Sounds
        manager.load(CoinSound, Sound.class);
        manager.load(FinishSound, Sound.class);
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
