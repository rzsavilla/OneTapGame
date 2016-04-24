package com.rzsavilla.onetapgame.Sprite.Enemy;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.Sprite.AnimatedSprite;
import com.rzsavilla.onetapgame.model.Shapes.Collision.AABB;

/**
 * Created by rzsavilla on 10/04/2016.
 */
public class Entity extends AnimatedSprite {
    private int m_iHealth;                  //Entity Health

    public AABB bb = new AABB();             //Bounding box for collision detection

    public void setHeath(int newHealth) {
        m_iHealth = newHealth;
    }

    /**
     * Draw bounding box, sprite and update bb transformations
     * @param p
     * @param c
     */
    public void draw(Paint p , Canvas c) {
        //Update bounding box to match sprite
        if (this.bPositionChanged) { bb.setPosition(this.getPosition());}
        if (this.bSizeChanged) { bb.setSize(this.getSize()); }
        if (this.bOriginChanged) {bb.setOrigin(this.getOrigin());}
        if (this.bScaleChanged) {bb.setScale(this.getScale());}

        bb.draw(p,c);           //Draw bounding box
        super.draw(p,c);        //override draw sprite
    }

    /**
     * Update Entity movement
     * @param timeStep
     */
    public void update(float timeStep) {
        this.moveUpdate(timeStep);
        this.updateAnimation();
    }
}