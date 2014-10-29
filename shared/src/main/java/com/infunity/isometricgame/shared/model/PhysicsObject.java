package com.infunity.isometricgame.shared.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.infunity.isometricgame.shared.model.maps.Map;

/**
 * Interface that needs to be implemented for every entity that have Box2D body.
 * Gives ability to run specific code on collision.
 * It also have methods that inform Box2DWorld class if object should be removed from Box2D World.
 */
public interface PhysicsObject {
    public void handleBeginContact(PhysicsObject psycho2, Map map);

    public Body getBody();
    public boolean getFlagForDelete();
    public void setFlagForDelete(boolean flag);
}
