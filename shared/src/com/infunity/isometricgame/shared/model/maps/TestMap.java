package com.infunity.isometricgame.shared.model.maps;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.infunity.isometricgame.shared.intefaces.EffectsInterface;
import com.infunity.isometricgame.shared.model.Box2DWorld;
import com.infunity.isometricgame.shared.utils.MapDescriptor;

/**
 * Sample Map class
 */
public class TestMap extends Map {

    public TestMap(Box2DWorld box2dworld, EffectsInterface effectManager) {
        super(box2dworld, effectManager);

        // Load map file with tileset
        tileMap = new TmxMapLoader().load("testmap.tmx");

        // Create Polygon objects from "collision" object layer
        MapProcessor.createGroundObjects(this, tileMap.getLayers().get("collision"), box2dworld);

        // Create coins from "coins" object layer
        MapProcessor.createCoins(this, tileMap.getLayers().get("coins"), box2dworld);

        // Set player position according to position of Ellipse object added to collision layer
        player.transform(MapProcessor.getPlayerPosition(this, tileMap.getLayers().get("collision")));

        tileWidth = (Integer)tileMap.getProperties().get("tilewidth");
        tileHeight = (Integer)tileMap.getProperties().get("tileheight");
        width = (Integer)tileMap.getProperties().get("width");
        height = (Integer)tileMap.getProperties().get("height");
    }

    /*
     * Creating map from MapDescriptor object. Used to load saved game.
     */
    public TestMap(MapDescriptor mapDesc, Box2DWorld box2dworld, EffectsInterface effectManager) {
        super(box2dworld, effectManager);

        // Load map file with tileset
        tileMap = new TmxMapLoader().load("testmap.tmx");

        // Create Polygon objects from "collision" object layer
        MapProcessor.createGroundObjects(this, tileMap.getLayers().get("collision"), box2dworld);

        // Create coins
        MapProcessor.createCoinsFromMapDesc(this, mapDesc, box2dworld);

        // Set player position
        player.transform(mapDesc.getPlayerPos());

        // Set gameTime
        gameTime = mapDesc.getGameTime();

        tileWidth = (Integer)tileMap.getProperties().get("tilewidth");
        tileHeight = (Integer)tileMap.getProperties().get("tileheight");
        width = (Integer)tileMap.getProperties().get("width");
        height = (Integer)tileMap.getProperties().get("height");

    }

    @Override
    public void dispose() {
        tileMap.dispose();
    }
}
