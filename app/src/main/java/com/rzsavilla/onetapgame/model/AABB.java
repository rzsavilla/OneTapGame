package com.rzsavilla.onetapgame.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Axis Aligned Bounding Box used for collision checks
 */
public class AABB extends Transformable implements Collidable{
    Vector2D m_vSize = new Vector2D(100,100);        //Width and Height
    Vector2D m_vExtents = new Vector2D(50,50);

    private int iColour = Color.WHITE;

    public AABB() {

    }

    public void setSize(Vector2D newSize) {
        m_vSize = newSize;
        m_vExtents = newSize.divide(2);
    }

    public void setSize(int width, int height) {
        m_vSize.x = width;
        m_vSize.y = height;
    }

    public Vector2D getSize() {
        return m_vSize;
    }

    public Vector2D getExtents() {
        return m_vExtents;
    }

    //Check if point is within box
    public boolean intersect(Vector2D point) {
        Vector2D min = this.getPosition();
        Vector2D max = new Vector2D();
        max.x = this.getPosition().x + m_vSize.x;
        max.y = this.getPosition().y + m_vSize.y;

        if (point.x >= min.x && point.x <= max.x &&
                point.y >= min.y && point.y <= max.y) {
            return true;
        } else {
            return false;
        }
    }

    //Check for collision with Box
    public boolean collision(AABB other) {
        Vector2D vMin = new Vector2D(this.getPosition().subtract(this.getOrigin()));
        Vector2D vMax = new Vector2D(vMin.x + this.getSize().x, vMin.y + this.getSize().y);

        Vector2D vOtherMin = new Vector2D(other.getPosition());
        Vector2D vOtherMax = new Vector2D(vOtherMin.x + other.getSize().x, other.getPosition().y + other.getSize().y);

        //if (vMax.x < vOtherMin.x || vMin.x > vOtherMax.x) {             //Check for x intersection
        //    return false;                                                   //No intersection
        //}
        //if (vMax.y < vOtherMin.x || vOtherMin.y > vOtherMax.y) {        //Check for y intersection
        //    return false;                                                   //No intersection
        //}
        //return true;        //Intersection has occured

        if (vMin.x < vOtherMax.x && vMax.x > vOtherMin.x &&
                vMin.y < vOtherMax.y && vMax.y > vOtherMin.y) {
            iColour = Color.RED;
            return true;

        } else {
            iColour = Color.WHITE;
            return false;
        }
    }

    //Check for collision intersection with circle
    public boolean collision(CircleShape other) {
        //Calculate vector between centre points
        Vector2D vDifference = new Vector2D(other.getPosition().subtract(this.getPosition()));
        //Clamp
        Vector2D vClamp = new Vector2D();
        if (vDifference.x >= 0) {
            vClamp.x = Math.min(vDifference.x,m_vExtents.x);
        } else {
            vClamp.x = Math.max(vDifference.x,-m_vExtents.x);
        }
        if (vDifference.y >= 0) {
            vClamp.y = Math.min(vDifference.y,m_vExtents.x);
        } else {
            vClamp.y = Math.max(vDifference.y,-m_vExtents.x);
        }
        //Calculate Distance from Rectangle edge to circle center
        Vector2D vDist = (vDifference.subtract(vClamp));

        //Calculate distance between box edge and circle edge/actual distance
        float fDistance = vDist.magnitude() - other.getRadius();
        if (fDistance <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void updateBB() {

    }

    public void draw(Paint p, Canvas c) {
        p.setColor(iColour);
        updateBB();
        if (bPositionChanged || bOriginChanged || bScaleChanged || bScaleChanged) {
            vGlobalPos = this.getPosition().subtract(this.getOrigin());
            //m_vSize.multiply(this.getScale());
            bPositionChanged = false;
            bOriginChanged = false;
            bScaleChanged = false;
            bScaleChanged = false;
        }
        c.drawRect(vGlobalPos.x, vGlobalPos.y,
                vGlobalPos.x + m_vSize.x, vGlobalPos.y + m_vSize.y, p);
    }
}
