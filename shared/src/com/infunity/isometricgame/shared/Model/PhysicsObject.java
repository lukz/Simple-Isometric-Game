package com.infunity.isometricgame.shared.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.infunity.isometricgame.shared.model.maps.Map;

/**
 * Created by Lukasz on 2014-10-12.
 */
public interface PhysicsObject {
    public void handleBeginContact(PhysicsObject psycho2, Map map);

    public Body getBody();
    public boolean getFlagForDelete();
    public void setFlagForDelete(boolean flag);
}
