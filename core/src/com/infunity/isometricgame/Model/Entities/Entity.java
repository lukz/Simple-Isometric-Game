package com.infunity.isometricgame.Model.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Lukasz on 2014-10-12.
 */
public abstract class Entity {

    protected Vector3 pos;
    protected float width;
    protected float height;
    protected Rectangle bounds;

    public Entity(float x, float y, float width, float height){
        this.pos = new Vector3(x, y, 0);
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void update(float delta);
    public abstract void dispose();

    /**
     * @return the position
     */
    public float getPositionX() {
        return pos.x;
    }

    public float getPositionY() {
        return pos.y;
    }

    /**
     * @param x,y the position to set
     */
    public void setPosition(float x, float y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    /**
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */

    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */

    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * @return the bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * @param bounds the bounds to set
     */
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

}
