package com.infunity.isometricgame.shared.Model.Maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.infunity.isometricgame.shared.Model.Box2DWorld;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class TestMap extends Map {

    public TestMap(Box2DWorld box2dworld) {
        super(box2dworld);

        tileMap = new TmxMapLoader().load("testmap.tmx");
        MapProcessor.createGroundObjects(this, tileMap.getLayers().get("collision"), box2dworld);
        System.out.println("finished!");
    }

    @Override
    public void dispose() {

    }
}
