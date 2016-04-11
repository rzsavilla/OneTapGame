package com.rzsavilla.onetapgame.model.Collision;

import android.graphics.Color;

import com.rzsavilla.onetapgame.model.Calculation;
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
            setColour(Color.RED);
            return true;
        }
        else {
            setColour(Color.WHITE);
            return false;
        }
    }
}
