package com.rzsavilla.onetapgame.model.Shapes.Collision;

import android.graphics.Color;
import android.util.Log;

import com.rzsavilla.onetapgame.model.Interface.Collidable;
import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
import com.rzsavilla.onetapgame.model.Shapes.RectangleShape;
import com.rzsavilla.onetapgame.model.Utilites.Calculation;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Axis Aligned Bounding Box used for collision checks
 */
public class AABB extends RectangleShape implements Collidable {

    /**
     * Collision test with AABB.
     */
    public boolean collision(AABB other) {
        if (this.getBounds().left < other.getBounds().right &&
                this.getBounds().right > other.getBounds().left &&
                this.getBounds().top < other.getBounds().bottom &&
                this.getBounds().bottom > other.getBounds().top) {
            setColour(Color.RED);
            other.setColour(Color.RED);
            return true;                    //Collision detected
        }
        setColour(Color.WHITE);
        other.setColour(Color.WHITE);
        return false;                       //No Collision
    }
    /**Collision check with circle */
    public boolean collision(CircleShape other) {
        Vector2D vHalfExt = new Vector2D(this.getWidth() / 2, this.getHeight() / 2);
        Vector2D vDiff = other.getPosition().subtract(this.getPosition().add(vHalfExt));

        Vector2D vClamp = new Vector2D();

        if (vDiff.x < 0) { vClamp.x = Math.max(vDiff.x, -vHalfExt.x); }
        else { vClamp.x = Math.min(vDiff.x, vHalfExt.x); }

        if (vDiff.y < 0) { vClamp.y = Math.max(vDiff.y, -vHalfExt.y); }
        else { vClamp.y = Math.min(vDiff.y, vHalfExt.y); }

        Vector2D vDist = vDiff.subtract(vClamp);

        float fDistance = vDist.magnitude() - other.getRadius();
        Log.d("Distance:",Float.toString(fDistance));
        if (fDistance <= 0) {
            this.setColour(Color.RED);
            other.setColour(Color.BLUE);
            return true;
        } else {
            this.setColour(Color.WHITE);
            other.setColour(Color.WHITE);
            return false;
        }
    }
    /**Collision check with AABB */
    public float intersect(AABB other) {
        return 0.0f;
    }
    /**Collision check with CircleShape */
    public float intersect(CircleShape other) {
        Vector2D vHalfExt = new Vector2D(this.getWidth() / 2, this.getHeight() / 2);
        Vector2D vDiff = other.getPosition().subtract(this.getPosition().add(vHalfExt));

        Vector2D vClamp = new Vector2D();

        if (vDiff.x < 0) { vClamp.x = Math.max(vDiff.x, -vHalfExt.x); }
        else { vClamp.x = Math.min(vDiff.x, vHalfExt.x); }

        if (vDiff.y < 0) { vClamp.y = Math.max(vDiff.y, -vHalfExt.y); }
        else { vClamp.y = Math.min(vDiff.y, vHalfExt.y); }

        Vector2D vDist = vDiff.subtract(vClamp);

        float fDistance = vDist.magnitude() - other.getRadius();
        return fDistance;
    }
    /*
    public float intersect(AABB other) {
        if (this.getBounds().left <= other.getBounds().right &&
                other.getBounds().left <= this.getBounds().right &&
                this.getBounds().top <= other.getBounds().bottom &&
                other.getBounds().top <= this.getBounds().bottom) {
            this.setColour(Color.RED);
            return true;
        }
        else {
            this.setColour(Color.WHITE);
            return false;
        }
    }
    */
}