package com.rzsavilla.onetapgame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.text.FieldPosition;
import java.util.Vector;

/**
 * Created by rzsavilla on 15/03/2016.
 */
public class Sprite extends Transformable {
    private Bitmap m_SpritesheetBMP;
    private Vector2Di m_viSize = new Vector2Di(100,100);
    private boolean m_bHasTexture = false;

    public Sprite() {

    }

    private void setSize(Vector2Di newSize) {
        m_viSize = newSize;
    }

    private void setSize(int width, int height) {
        m_viSize.x = width;
        m_viSize.y = height;
    }

    public void setTexture(Bitmap bitmapIn) {
        m_SpritesheetBMP = m_SpritesheetBMP.createScaledBitmap(bitmapIn,bitmapIn.getWidth(),bitmapIn.getHeight(), false);
        m_bHasTexture = true;
    }

    public void drawSprite (Paint p,Canvas c) {
        if (m_bHasTexture) {
            c.rotate(getRotation(), getPosition().x, getPosition().y);
            c.drawBitmap(m_SpritesheetBMP, getPosition().x - getOrigin().x, getPosition().y - getOrigin().y, p);
            c.rotate(-getRotation(), getPosition().x, getPosition().y);
        }
    }

    public void update() {

    }
}
