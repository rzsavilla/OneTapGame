package com.rzsavilla.onetapgame.model;

import android.graphics.Rect;
import android.graphics.RectF;
import android.renderscript.Matrix3f;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public class Transformable extends Destroyable{
    protected Vector2D vGlobalPos = new Vector2D();
    private Vector2D m_vPosition = new Vector2D();
    private Vector2D m_vOrigin = new Vector2D();
    private Vector2D m_vScale = new Vector2D(1.0f,1.0f);
    private RectF m_vGlobalBounds = new RectF();
    private float m_fRotation = 0.0f;
    private boolean bUpdate = false;

    protected boolean bPositionChanged = true;
    protected boolean bOriginChanged = true;
    protected boolean bRotationChanged = true;
    protected boolean bScaleChanged = true;

    /////////////////SET///////////////////////////
    public void setPosition(Vector2D newPosition) {
        m_vPosition = newPosition;
        bPositionChanged = true;
    }

    public void setPosition(float x, float y) {
        m_vPosition.x = x;
        m_vPosition.y = y;
        bPositionChanged = true;
    }

    public void setOrigin(Vector2D newOrigin) {
        m_vOrigin = newOrigin;
    }

    public void setOrigin(float x, float y) {
        m_vOrigin.x = x;
        m_vOrigin.y = y;
    }

    public void setScale(Vector2D newScale) {
        m_vScale = newScale;
    }

    public void setScale(float x, float y) {
        m_vScale.x = x;
        m_vScale.y = y;
    }

    public void setRotatation(float newRotation) {
        m_fRotation = newRotation;
        bRotationChanged = true;
    }

    ////////////////GET/////////////////////
    public Vector2D getPosition() {
        return  m_vPosition;
    }

    public Vector2D getOrigin() {
        return m_vOrigin;
    }

    public Vector2D getScale() {
        return m_vScale;
    }

    public float getRotation() {
        return m_fRotation;
    }

    ////////////////TRANSFORMATION//////////////////
}
