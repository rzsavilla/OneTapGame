package com.rzsavilla.onetapgame.model.Inherited;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Animation.AnimatedSprite;
import com.rzsavilla.onetapgame.model.Collision.AABB;
import com.rzsavilla.onetapgame.model.Collision.Circle;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

/**
 * Created by rzsavilla on 10/04/2016.
 */
public class Entity extends AnimatedSprite {
    private int m_iHealth;                  //Entity Health
    private boolean m_bDeath = false;       //Flag for death animation

    public AABB bb = new AABB();             //Bounding box for collision detection

    public void setHeath(int newHealth) {
        m_iHealth = newHealth;
    }

    public void updateEntity() {

    }

    public void draw(Paint p , Canvas c) {
        if (this.bPositionChanged) { bb.setPosition(this.getPosition());}
        if (this.bSizeChanged) { bb.setSize(this.getSize()); }
        if (this.bOriginChanged) {bb.setOrigin(this.getOrigin());}
        if (this.bScaleChanged) {bb.setScale(this.getScale());}

        bb.draw(p,c);
        super.draw(p,c);        //override draw

    }
}