package com.rzsavilla.onetapgame.model.Shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Abstract.Moveable;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public class CircleShape extends Moveable {
    private float m_fRadius = 50.0f;
    private int m_iColour = 0;

    //Default Constructor
    public CircleShape() {

    }

    public CircleShape(float posX, float posY,float newRadius, int newColour) {
        setPosition(posX,posY);
        setRadius(newRadius);
        setColour(newColour);
    }

    public void setRadius(float newRadius) {
        m_fRadius = newRadius;
    }

    public void setColour(int newColour) {
        m_iColour = newColour;
    }

    public float getRadius() {
        return m_fRadius;
    }

    public int getColour() {
        return m_iColour;
    }

    public void draw(Paint p, Canvas c) {
        p.setColor(m_iColour);
        if (bPositionChanged || bOriginChanged || bScaleChanged) {
            updateGlobalBounds();
        }
        c.drawCircle(getPosition().x, getPosition().y, m_fRadius,p);
    }
}
