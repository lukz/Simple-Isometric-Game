package com.infunity.isometricgame.Utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Lukasz on 2014-10-12.
 */
public class Box2DWorld {

    /*
	 * Statics for calculation pixel to box2d metrics and vice versa
	 */
    public static final float WORLD_TO_BOX = 0.01f;
    public static final float BOX_TO_WORLD = 100f;

    private World world;
    private Box2DDebugRenderer renderer;

//    private FixtureDefBuilder fixtureDefBuilder;
//    private BodyBuilder bodyBuilder;

    public Box2DWorld(Vector2 gravity) {
//        World.setVelocityThreshold(WORLD_TO_BOX);
        world = new World(gravity, true);
        renderer = new Box2DDebugRenderer();
    }

    public void update(float dt) {
        world.step(dt, 6, 2);
        //sweepDeadBodies();
    }

    /*
	 * Bodies should be removed after world step to prevent simulation crash
	 */
//	public void sweepDeadBodies() {
//		world.getBodies(bodies);
//		for (Iterator<Body> iter = bodies.iterator(); iter.hasNext();) {
//			Body body = iter.next();
//			if (body != null && (body.getUserData() instanceof Player)) {
//				Player data = (Player) body.getUserData();
//				if (data.isFlaggedForDelete) {
//					getWorld().destroyBody(body);
//					body.setUserData(null);
//					body = null;
//				}
//			}
//		}
//	}

    /*
	 * Render box2d debug
     */
    public void render(Camera cam) {
        renderer.render(world, cam.combined.cpy().scl(BOX_TO_WORLD));
    }

    public World getWorld() {
        return world;
    }

}
