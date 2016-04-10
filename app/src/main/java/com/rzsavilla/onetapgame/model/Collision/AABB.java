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
        return false;
    }
}