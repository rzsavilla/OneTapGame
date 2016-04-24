package com.rzsavilla.onetapgame.model.Shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Utilites.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public class RectangleShape extends Transformable{
    private int m_iColour = Color.WHITE;

    //Default Constructor
    public RectangleShape() {

    }

    public RectangleShape(float posX, float posY, float width, float height, int newColour) {
        setPosition(posX,posY);
        setSize(width, height);
        setColour(newColour);
        setOrigin(width / 2, height / 2);
    }

    public RectangleShape(Vector2D newPosition, Vector2D newSize, int newColour) {
        this.setPosition(newPosition);
        this.setSize(newSize);
        this.setColour(newColour);
    }

    public void setColour(int newColour) {
        m_iColour = newColour;
    }

    public int getColour() {
        return m_iColour;
    }

    public void draw(Paint p, Canvas c) {
        if (bPositionChanged || bOriginChanged || bScaleChanged) {
            updateGlobalBounds();
        }
        p.setColor(m_iColour);
        c.rotate(this.getRotation());
        c.drawRect(getBounds(),p);
        c.rotate(-this.getRotation());
    }
}
