package com.infunity.isometricgame.shared.Model.Maps;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.infunity.isometricgame.shared.Model.Box2DWorld;
import net.dermetfan.gdx.math.GeometryUtils;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

/**
 * Created by Lukasz on 2014-10-14.
 */
public class MapProcessor {

    public static void createGroundObjects(Map map, MapLayer layer, Box2DWorld world) {

        Matrix4 transformMat4 = getTransformationMatrix(map);

        for(MapObject object : layer.getObjects()) {
            if(object instanceof PolygonMapObject) {
                polygonGround(object, map, world, transformMat4);
            }
        }
    }

    /**
     * Some fancy math to transform object vertices according to map orientation <br />
     * @see <a href="http://badlogicgames.com/forum/viewtopic.php?p=64881#p6488">LibGDX Forum</a>
     * @see <a href="https://bitbucket.org/dermetfan/libgdx-utils/src/d0dc140b9951d5b8b12dcf776f2eb7779b97b747/box2d/src/main/net/dermetfan/utils/libgdx/box2d/Box2DMapObjectParser.java#cl-542">Box2DMapObjectParser.java source</a>
     *
     */
    private static Matrix4 getTransformationMatrix(Map map) {
        String mapOrientation = (String)map.getTileMap().getProperties().get("orientation");
        Matrix4 transformMat4 = new Matrix4();
        transformMat4.idt();

        if(mapOrientation.equals("isometric")) {
            transformMat4.scale((float) (Math.sqrt(2) / 2), (float) (Math.sqrt(2) / 4), 1);
            transformMat4.rotate(0, 0, 1, -45);
            transformMat4.translate(-1, 1, 0);
            transformMat4.scale(2, 2, 2);
        } else if(mapOrientation.equals("staggered")) {
            int mapHeight = (Integer)map.tileMap.getProperties().get("height");
            int tileWidth = (Integer)map.tileMap.getProperties().get("tilewidth");
            int tileHeight = (Integer)map.tileMap.getProperties().get("tileheight");
            transformMat4.translate(-tileWidth / 2, -tileHeight * (mapHeight / 2) + tileHeight / 2, 0);
        }

        return transformMat4;
    }

    private static void polygonGround(MapObject object, Map map, Box2DWorld world, Matrix4 transformMat4) {
        PolygonMapObject polyObject = (PolygonMapObject)object;
        Polygon polygon = new Polygon(polyObject.getPolygon().getTransformedVertices());
        polygon.setScale(world.WORLD_TO_BOX, world.WORLD_TO_BOX);

        Vector3 tempVec3 = new Vector3();

        // Transform each vertex by transformation matrix
        for(int ix = 0, iy = 1; iy < polygon.getVertices().length; ix += 2, iy += 2) {
            tempVec3.set(polygon.getVertices()[ix], polygon.getVertices()[iy], 0);
            tempVec3.mul(transformMat4);
            polygon.getVertices()[ix] = tempVec3.x;// - body.getPosition().x;
            polygon.getVertices()[iy] = tempVec3.y;// - body.getPosition().y;
        }

        Polygon[] convexPolygons = GeometryUtils.decompose(polygon);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.fixedRotation = true;
        Body body = world.getWorld().createBody(bd);

        for(Polygon convexPolygon : convexPolygons) {
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.density = 1f;
            fixtureDef.friction = 0.0f;
            fixtureDef.restitution = 0.0f;

            PolygonShape shape = new PolygonShape();

            shape.set(convexPolygon.getTransformedVertices());
            fixtureDef.shape = shape;

            body.createFixture(fixtureDef);
        }

        body.setUserData(map);
    }

    public static Vector2 getPlayerPosition(Map map, MapLayer layer) {
        Vector2 playerPos = new Vector2(0,0);

        for(MapObject object : layer.getObjects()) {
            if(object instanceof EllipseMapObject) {
                EllipseMapObject mapObject = (EllipseMapObject)object;
                Vector3 tempVec3 = new Vector3();
                tempVec3.set(mapObject.getEllipse().x + mapObject.getEllipse().width / 2,
                        mapObject.getEllipse().y + mapObject.getEllipse().height / 2, 0);
                tempVec3.mul(getTransformationMatrix(map));

                playerPos.set(tempVec3.x, tempVec3.y);
            }
        }

        return playerPos;
    }

}
