package com.rzsavilla.onetapgame.model.Collision;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.rzsavilla.onetapgame.model.Drawable;
import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
import com.rzsavilla.onetapgame.model.Shapes.RectangleShape;
import com.rzsavilla.onetapgame.model.Utilites.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

/**
 * Axis Aligned Bounding Box used for collision checks
 */
public class AABB extends RectangleShape implements Collidable{
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

    public boolean collision(CircleShape other) {
        //Distance between AABB and Circle centres
        Vector2D vDist = new Vector2D(this.getPosition().subtract(other.getPosition()));

        //Set Clamp
        Vector2D vClamp = new Vector2D();
        if (vDist.x < 0) { vClamp.x = Math.max(vDist.x,-other.getWidth() / 2); }
        else if (vDist.x >= 0) { vClamp.x = Math.min(vDist.x, other.getWidth() / 2); }
        if (vDist.y < 0 ) { vClamp.y = Math.max(vDist.y,-other.getHeight() / 2); }
        else if (vDist.y >= 0) { vClamp.y = Math.min(vDist.y, other.getHeight() / 2); }

        Vector2D vDiff = vDist.subtract(vClamp);

        float fDistance = vDiff.magnitude() - other.getRadius();         //Edge to edge distance
        //System.out.println(fDistance);
        if (fDistance <= 0) {
            this.setColour(Color.RED);
            return true;
        } else {
            this.setColour(Color.WHITE);
            return false;
        }
    }

    public boolean intersect(AABB other) {
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
}