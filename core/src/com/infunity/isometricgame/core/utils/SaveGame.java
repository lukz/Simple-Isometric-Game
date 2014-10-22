package com.infunity.isometricgame.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.infunity.isometricgame.core.IsometricGame;
import com.infunity.isometricgame.core.screens.GameScreen;
import com.infunity.isometricgame.core.view.EffectManager;
import com.infunity.isometricgame.core.view.ParticleManager;
import com.infunity.isometricgame.shared.model.Box2DWorld;
import com.infunity.isometricgame.shared.model.maps.Map;
import com.infunity.isometricgame.shared.model.maps.TestMap;
import com.infunity.isometricgame.shared.utils.MapDescriptor;

public class SaveGame {

    private static Preferences prefs = Gdx.app.getPreferences("isometricgame_prefs");
    private static Json json = new Json();

    public static void saveGame(Map map) {
        MapDescriptor mapDesc = new MapDescriptor();
        mapDesc.setMapDescriptor(map);

        Array<MapDescriptor> saveGames;

        if(prefs.getString("saveGame", null) != null) {
            // Load array of existing saves
            saveGames = json.fromJson(Array.class, prefs.getString("saveGame"));
        } else {
            // Create new array for saves
            saveGames = new Array<MapDescriptor>();
        }

        saveGames.add(mapDesc);

        prefs.putString("saveGame", json.toJson(saveGames, Array.class));

        prefs.flush();
    }

    public static Array<MapDescriptor> getSaveGames() {
        Array<MapDescriptor> saveGames;

        if(prefs.getString("saveGame", null) != null) {
            saveGames = json.fromJson(Array.class, prefs.getString("saveGame"));
        } else {
            saveGames = new Array<MapDescriptor>();
        }

        return saveGames;
    }

    public static void deleteSaveGame(int id) {
        Array<MapDescriptor> saveGames = getSaveGames();

        if(id < saveGames.size) {
            saveGames.removeIndex(id);
            prefs.putString("saveGame", json.toJson(saveGames, Array.class));
            prefs.flush();
        }
    }

    public static void loadGame(IsometricGame game, MapDescriptor mapDesc) {
        Box2DWorld box2dworld = new Box2DWorld(new Vector2(0, 0));
        Map map = new TestMap(mapDesc, box2dworld, new EffectManager(new ParticleManager()));

        game.setScreen(new GameScreen(game, map, box2dworld));
    }
}
