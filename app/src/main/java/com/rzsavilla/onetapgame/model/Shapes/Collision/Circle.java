package com.rzsavilla.onetapgame.model.Shapes.Collision;

import android.graphics.Color;

import com.rzsavilla.onetapgame.model.Interface.Collidable;
import com.rzsavilla.onetapgame.Sprite.Enemy.Entity;
import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Circle for collision tests
 * @author rzsavilla
 */
public class Circle extends CircleShape implements Collidable {
    /**Default Constructor*/
    public Circle() {

    }
    public Circle(float posX, float posY,float newRadius, int newColour) {
        super(posX,posY,newRadius,newColour);
    }
    /**Collision check with AABB*/
    public boolean collision(AABB other) {
        //Distance between AABB and Circle centres
        Vector2D vExtents = this.getSize().divide(2.0f);
        Vector2D vDist = new Vector2D(this.getPosition().subtract((other.getPosition().add(vExtents))));
        //Set Clamps
        Vector2D vClamp = new Vector2D();

        if (vDist.x < 0) { vClamp.x = Math.max(vDist.x,-vExtents.x); }
        else if (vDist.x >= 0) { vClamp.x = Math.min(vDist.x, vExtents.x); }
        if (vDist.y < 0 ) { vClamp.y = Math.max(vDist.y,-vExtents.x); }
        else if (vDist.y >= 0) { vClamp.y = Math.min(vDist.y, vExtents.x); }

        Vector2D vDiff = vDist.subtract(vClamp);

        float fDistance = vDiff.magnitude() - this.getRadius();         //Edge to edge distance
        //System.out.println(fDistance);
        if (fDistance <= 0) {
            return true;
        } else {
            return false;
        }
    }
    /**Collision check with CircleShape*/
    public boolean collision(CircleShape other) {
        Vector2D vDist = new Vector2D(this.getPosition().subtract(other.getPosition()));

        float fDistance = vDist.magnitude() - (this.getRadius() + other.getRadius());
        //System.out.println(fDistance);
        if (fDistance <= 0) {
            return true;
        }
        else {
            return false;
        }
    }
    /**Collision check with AABB */
    public float intersect(AABB other) {
        //Distance between AABB and Circle centres
        Vector2D vExtents = this.getSize().divide(2.0f);
        Vector2D vDist = new Vector2D(this.getPosition().subtract((other.getPosition().add(vExtents))));
        //Set Clamps
        Vector2D vClamp = new Vector2D();

        if (vDist.x < 0) { vClamp.x = Math.max(vDist.x,-vExtents.x); }
        else if (vDist.x >= 0) { vClamp.x = Math.min(vDist.x, vExtents.x); }
        if (vDist.y < 0 ) { vClamp.y = Math.max(vDist.y,-vExtents.x); }
        else if (vDist.y >= 0) { vClamp.y = Math.min(vDist.y, vExtents.x); }

        Vector2D vDiff = vDist.subtract(vClamp);

        float fDistance = vDiff.magnitude() - this.getRadius();         //Edge to edge distance
        //System.out.println(fDistance);
        return fDistance;
    }
    /**Collision check CircleShape*/
    public float intersect(CircleShape other) {
        Vector2D vDist = new Vector2D(this.getPosition().subtract(other.getPosition()));

        float fDistance = vDist.magnitude() - (this.getRadius() + other.getRadius());
        //System.out.println(fDistance);
        return fDistance;
    }

    /**
     * Collision resolution with Circle
     * @param other
     * @return
     */
    public boolean impulse(Circle other) {
        if (collision(other)) {
            Vector2D vDiff = this.getPosition().subtract(other.getPosition()); //Centre Differenc
            Vector2D vNormal = vDiff.unitVector();

            Vector2D vDifference = this.getVelocity().subtract(other.getVelocity());

            float n = vNormal.dot(vDifference);

            float e = -2;   //Perfect collision

            float massDivide =((1/ this.getMass()) + (1 / other.getMass()));
            float j = (e * n) / massDivide;

            Vector2D jn1 = (vNormal.multiply(j)).divide(this.getMass());
            Vector2D jn2 = (vNormal.multiply(j).divide(other.getMass()));

            this.setVelocity(this.getVelocity().add(jn1));
            other.setVelocity(other.getVelocity().subtract(jn2));

            //Correct the collision Ensures that objects are not overlapping

            return true;
        }
        return false;
    }
    /**
     * Collision resolution with Entity
     * @param other
     * @return
     */
    public boolean impulse(Entity other) {
        if (collision(other.bb)) {
            Vector2D vDiff = this.getPosition().subtract(other.getPosition()); //Centre Differenc
            Vector2D vNormal = vDiff.unitVector();

            Vector2D vDifference = this.getVelocity().subtract(other.getVelocity());

            float n = vNormal.dot(vDifference);

            float e = -4;   //Perfect collision

            float massDivide =((1/ this.getMass()) + (1 / other.getMass()));
            float j = (e * n) / massDivide;

            Vector2D jn1 = (vNormal.multiply(j)).divide(this.getMass());
            Vector2D jn2 = (vNormal.multiply(j).divide(other.getMass()));

            this.setVelocity(this.getVelocity().add(jn1));
            other.setVelocity(other.getVelocity().subtract(jn2));
            return true;
        }
        return false;
    }
}
