package com.rzsavilla.onetapgame.model.Collision;

import android.graphics.Color;

import com.rzsavilla.onetapgame.model.Calculation;
import com.rzsavilla.onetapgame.model.Inherited.Entity;
import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Collidable Circle
 */
public class Circle extends CircleShape implements Collidable {

    public Circle() {

    }

    public boolean collision(AABB other) {
        //Distance between AABB and Circle centres
        Vector2D vDist = new Vector2D(this.getPosition().subtract(other.getPosition()));

        //Set Clamp
        Vector2D vClamp = new Vector2D();
        if (vDist.x < 0) { vClamp.x = Math.max(vDist.x,-other.getWidth() / 2); }
        else if (vDist.x >= 0) { vClamp.x = Math.min(vDist.x, other.getWidth() / 2); }
        if (vDist.y < 0 ) { vClamp.y = Math.max(vDist.y,-other.getHeight() / 2); }
        else if (vDist.y >= 0) { vClamp.y = Math.min(vDist.y, other.getHeight() / 2); }

        Vector2D vDiff = vDist.subtract(vClamp);

        float fDistance = vDiff.magnitude() - this.getRadius();         //Edge to edge distance
        //System.out.println(fDistance);
        if (fDistance <= 0) {
            this.setColour(Color.RED);
            return true;
        } else {
            this.setColour(Color.WHITE);
            return false;
        }
    }
    public boolean collision(CircleShape other) {
        Vector2D vDist = new Vector2D(this.getPosition().subtract(other.getPosition()));

        float fDistance = vDist.magnitude() - (this.getRadius() + other.getRadius());
        //System.out.println(fDistance);
        if (fDistance <= 0) {
            this.setColour(Color.RED);
            return true;
        }
        else {
            this.setColour(Color.WHITE);
            return false;
        }
    }

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

    public boolean impulse(Entity other) {
        if (collision(other.bb)) {
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
            return true;
        }
        return false;
    }
}
