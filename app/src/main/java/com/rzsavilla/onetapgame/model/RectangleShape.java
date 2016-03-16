package com.rzsavilla.onetapgame.model;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public class RectangleShape extends Moveable{
    private Vector2D m_vSize = new Vector2D();
    private int m_iColour = 0;

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

    public void setSize(Vector2D newSize) {
        m_vSize = newSize;
    }

    public void setSize(float width, float height) {
        m_vSize.x = width;
        m_vSize.y = height;
    }

    public void setColour(int newColour) {
        m_iColour = newColour;
    }

    public Vector2D getSize() {
        return m_vSize;
    }

    public int getColour() {
        return m_iColour;
    }

    public void draw(Paint p, Canvas c) {
        p.setColor(m_iColour);
        c.rotate(this.getRotation());
        c.drawRect(getPosition().x - getOrigin().x, getPosition().y - getOrigin().y,
                getPosition().x + m_vSize.x - getOrigin().x, getPosition().y + m_vSize.y - getOrigin().y, p);

        c.rotate(-this.getRotation());
    }
}
