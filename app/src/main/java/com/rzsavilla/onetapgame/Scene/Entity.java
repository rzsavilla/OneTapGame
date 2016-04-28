package com.rzsavilla.onetapgame.Scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.Sprite.AnimatedSprite;
import com.rzsavilla.onetapgame.model.Shapes.Collision.AABB;
import com.rzsavilla.onetapgame.model.Shapes.Collision.Circle;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Created by rzsavilla on 10/04/2016.
 */
public class Entity extends AnimatedSprite {
    //private int m_iHealth;                  //Entity Health

    public Circle bb = new Circle(0.0f,0.0f,100.0f, Color.RED);             //Bounding box for collision detection

    //public void setHealth(int newHealth) {
    //    m_iHealth = newHealth;
    //}
    //public int getHealth() { return m_iHealth; }
    //public void damage(int damage) { m_iHealth -= damage; }

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

        //bb.draw(p, c);           //Draw bounding box
        super.draw(p, c);        //override draw sprite
    }

    /**
     * Update Entity movement
     * @param timeStep
     */
    public void update(float timeStep) {
        this.moveUpdate(timeStep);
        this.updateAnimation();
    }

    public boolean impulse(Entity other) {
        float fOverlap = this.bb.intersect(other.bb);
        if (fOverlap < 0.0f) {
            Vector2D vDiff = this.getPosition().subtract(other.getPosition()); //Centre Differenc
            Vector2D vNormal = vDiff.unitVector();

            Vector2D vDifference = this.getVelocity().subtract(other.getVelocity());

            float n = vNormal.dot(vDifference);

            float e = -2.0f;   //Collision Response

            float massDivide =((1/ this.getMass()) + (1 / other.getMass()));
            float j = (e * n) / massDivide;

            Vector2D jn1 = (vNormal.multiply(j)).divide(this.getMass());
            Vector2D jn2 = (vNormal.multiply(j).divide(other.getMass()));

            this.setVelocity(this.getVelocity().add(jn1));
            other.setVelocity(other.getVelocity().subtract(jn2));

            ////Correct the collision preventing overlap;
            //float fAngle = (float)Math.atan2(vDiff.y,vDiff.x) +(float)Math.PI;
            //Vector2D vCorrection = new Vector2D((float)Math.cos(fAngle) * fOverlap,(float)Math.sin(fAngle) * fOverlap);
            //this.setPosition(this.getPosition().add(vCorrection));
            return true;
        }
        return false;
    }

    /**
     * Collision reponse against immovable object
     * @param other
     * @return
     */
    public boolean impulseStatic(AABB other) {
        if (other.collision(this.bb)) {
            Vector2D vDiff;
            Vector2D vNormal;
            float n;
            float e;
            e = -5.0f;
            vDiff = this.getPosition().subtract(other.getPosition()); //Centre Differenc
            vNormal = vDiff.unitVector();
            n = this.getVelocity().dot(vNormal);
            vNormal.multiply(n);
            vNormal.multiply(e);
            Vector2D newVel = this.getVelocity().multiply(vNormal);
            this.setVelocity(newVel);

            return  true;
        }
        return false;
    }

    public boolean impulseStatic(Circle other) {
        if (other.collision(this.bb)) {
            Vector2D vDiff;
            Vector2D vNormal;
            float n;
            float e;
            e = -5.0f;
            vDiff = this.getPosition().subtract(other.getPosition()); //Centre Differenc
            vNormal = vDiff.unitVector();
            n = this.getVelocity().dot(vNormal);
            vNormal.multiply(n);
            vNormal.multiply(e);
            Vector2D newVel = this.getVelocity().multiply(vNormal);
            this.setVelocity(newVel);
            return  true;
        }
        return false;
    }
}