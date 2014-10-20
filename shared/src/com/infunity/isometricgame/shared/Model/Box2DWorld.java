package com.infunity.isometricgame.shared.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

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

    // Array used to sweep dead bodies
    private Array<Body> bodies;

    public Box2DWorld(Vector2 gravity) {
//        World.setVelocityThreshold(WORLD_TO_BOX);
        world = new World(gravity, true);
        renderer = new Box2DDebugRenderer();

        bodies = new Array<Body>();
    }

    public void update(float dt) {
        world.step(dt, 6, 2);
        sweepDeadBodies();
    }

    /*
	 * Bodies should be removed after world step to prevent simulation crash
	 */
	public void sweepDeadBodies() {
		world.getBodies(bodies);
		for (Iterator<Body> iter = bodies.iterator(); iter.hasNext();) {
			Body body = iter.next();
			if (body != null && (body.getUserData() instanceof PhysicsObject)) {
                PhysicsObject data = (PhysicsObject) body.getUserData();
				if (data.getFlagForDelete()) {
					getWorld().destroyBody(body);
					body.setUserData(null);
					body = null;
				}
			}
		}
	}

    public Body createPlayerBody(float x, float y, float width, float height,
                                BodyDef.BodyType type, boolean fixedRotation) {
        PolygonShape ps = new PolygonShape();

        float[] vertices = new float[8];

        vertices[0] = -width * WORLD_TO_BOX / 2f;
        vertices[1] = 0;

        vertices[2] = 0;
        vertices[3] = -height * WORLD_TO_BOX / 2f;

        vertices[4] = width * WORLD_TO_BOX / 2f;
        vertices[5] = 0;

        vertices[6] = 0;
        vertices[7] = height * WORLD_TO_BOX / 2f;

        ps.set(vertices);

        FixtureDef fdef = new FixtureDef();
        fdef.density = 1f;
        fdef.friction = 0.0f;
        fdef.restitution = 0.0f;
        fdef.shape = ps;

        BodyDef bd = new BodyDef();
        bd.allowSleep = true;
        bd.position.set(x * WORLD_TO_BOX, y * WORLD_TO_BOX);
        bd.fixedRotation = fixedRotation;

        Body body = world.createBody(bd);
        body.createFixture(fdef);
        body.setType(type);

        return body;
    }

    public Body createRectangle(float x, float y, float width, float height,
                                BodyDef.BodyType type, boolean fixedRotation, boolean isSensor) {

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width * WORLD_TO_BOX / 2f, height * WORLD_TO_BOX / 2f);

        FixtureDef fdef = new FixtureDef();
        fdef.density = 1f;
        fdef.friction = 0.0f;
        fdef.restitution = 0.0f;
        fdef.shape = ps;
        fdef.isSensor = isSensor;

        BodyDef bd = new BodyDef();
        bd.allowSleep = true;
        bd.position.set(x * WORLD_TO_BOX, y * WORLD_TO_BOX);
        bd.fixedRotation = fixedRotation;

        Body body = world.createBody(bd);
        body.createFixture(fdef);
        body.setType(type);



        return body;
    }

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
