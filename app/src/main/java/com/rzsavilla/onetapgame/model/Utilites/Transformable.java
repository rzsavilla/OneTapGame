package com.rzsavilla.onetapgame.model.Utilites;

import android.graphics.RectF;

import com.rzsavilla.onetapgame.Scene.Warrior;
import com.rzsavilla.onetapgame.model.Abstract.Destroyable;

import java.io.Serializable;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public class Transformable extends Destroyable{
    private Vector2D m_vPosition = new Vector2D(0.0f,0.0f);           //x and y position
    private Vector2D m_vOrigin = new Vector2D(0.0f,0.0f);             //Point of rotation scale and centre
    private Vector2D m_vScale = new Vector2D(1.0f,1.0f);              //Default/Original size is 1.f;
    private Vector2D m_vSize = new Vector2D(0.0f,0.0f);           //Width and height
    private float m_fRotation = 0.0f;                                 //Rotation angle
    protected RectF m_GlobalBounds = new RectF(0.0f,0.0f,0.0f,0.0f);  //Transformation applied

    //Flags to notify derived classes that transformation has occurred, classes can then decide if it is necessary to update transformation.
    protected boolean bPositionChanged = false;
    protected boolean bOriginChanged = false;
    protected boolean bRotationChanged = false;
    protected boolean bScaleChanged = false;
    protected boolean bSizeChanged = false;

    /////////////////SET///////////////////////////
    public Warrior setPosition(Vector2D newPosition) {
        m_vPosition = newPosition;
        bPositionChanged = true;            //Indicate/Notify change has occured
        return null;
    }
    public void setPosition(float x, float y) {
        m_vPosition.x = x;
        m_vPosition.y = y;
        bPositionChanged = true;
    }
    public void setOrigin(Vector2D newOrigin) {
        m_vOrigin = newOrigin;
        bOriginChanged = true;
    }
    public void setOrigin(float x, float y) {
        m_vOrigin.x = x;
        m_vOrigin.y = y;
        bOriginChanged = true;
    }
    public void setScale(Vector2D newScale) {
        m_vScale = newScale;
        bScaleChanged = true;
    }
    public void setScale(float x, float y) {
        m_vScale.x = x;
        m_vScale.y = y;
        bScaleChanged = true;
    }
    public void setRotatation(float newRotation) {
        m_fRotation = newRotation;
        bRotationChanged = true;
    }
    public void setSize(Vector2D newSize) {
        m_vSize = newSize;
        bSizeChanged = true;
    }
    public void setSize(float width, float height) {
        m_vSize.x = width;
        m_vSize.y = height;
        bSizeChanged = true;
    }
    public void setWidth(float width) { m_vSize.x = width; }
    public void setHeight(float height) { m_vSize.y = height; }
    public void setBounds(RectF newBounds) { m_GlobalBounds = newBounds; }

    ////////////////GET/////////////////////
    public Vector2D getPosition() { return  m_vPosition; }
    public Vector2D getOrigin() { return m_vOrigin; }
    public Vector2D getScale() { return m_vScale; }
    public Vector2D getSize() { return m_vSize; }
    public float getRotation() { return m_fRotation; }
    public float getWidth() { return m_vSize.x; }
    public float getHeight() {return m_vSize.y; }
    public RectF getBounds() { return m_GlobalBounds; }

    ////////////////TRANSFORMATION//////////////////
    public void updateGlobalBounds() {
        m_GlobalBounds = new RectF(
                (getPosition().x - getOrigin().x) * getScale().x,
                (getPosition().y - getOrigin().y) * getScale().y,
                (getPosition().x - getOrigin().x + m_vSize.x) * getScale().x,
                (getPosition().y - getOrigin().y + m_vSize.y) * getScale().y);

        m_GlobalBounds.left = (getPosition().x - getOrigin().x) * getScale().x;
        m_GlobalBounds.top = (getPosition().y - getOrigin().y) * getScale().y;
        m_GlobalBounds.right =  (getPosition().x - getOrigin().x + m_vSize.x) * getScale().x;
        m_GlobalBounds.bottom = (getPosition().y - getOrigin().y + m_vSize.y) * getScale().y;

        //Reset flags
        bScaleChanged = false;
        bPositionChanged = false;
        bOriginChanged = false;
        bSizeChanged = false;
    }
}
