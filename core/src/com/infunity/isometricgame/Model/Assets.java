package com.infunity.isometricgame.Model;

import com.badlogic.gdx.assets.AssetManager;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class Assets {

    private AssetManager manager;

    public Assets() {
        manager = new AssetManager();

        enqueueAssets();
        //manager.finishLoading();
    }

    public void enqueueAssets() {

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
