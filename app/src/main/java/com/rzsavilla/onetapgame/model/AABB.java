package com.rzsavilla.onetapgame.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by rzsavilla on 17/03/2016.
 */
public class AABB extends Transformable {
    Vector2Di m_viSize = new Vector2Di(100,100);        //Width and Height

    public AABB() {

    }

    public void setSize(Vector2Di newSize) {
        m_viSize = newSize;
    }

    public void setSize(int width, int height) {
        m_viSize.x = width;
        m_viSize.y = height;
    }

    public Vector2Di getSize() {
        return m_viSize;
    }

    public boolean intersect(Vector2D point) {
        Vector2D min = this.getPosition();
        Vector2D max = new Vector2D();
        max.x = this.getPosition().x + m_viSize.x;
        max.y = this.getPosition().y + m_viSize.y;

        if (point.x >= min.x && point.x <= max.x &&
                point.y >= min.y && point.y <= max.y) {
            return true;
        } else {
            return false;
        }
    }

    public void draw(Paint p, Canvas c) {
        p.setColor(Color.WHITE);
        c.drawRect(getPosition().x,getPosition().y,
                   getPosition().x + m_viSize.x, getPosition().y + m_viSize.y,p);
    }
}
