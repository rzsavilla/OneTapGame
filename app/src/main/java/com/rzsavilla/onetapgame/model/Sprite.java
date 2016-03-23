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
public class Sprite extends Moveable implements Drawable, Collidable{
    protected Bitmap m_SpritesheetBMP;
    public Vector2Di m_viSize = new Vector2Di(100,100);
    protected boolean m_bHasTexture = false;
    private float e = 1;

    protected Rect src = new Rect();
    protected RectF dst = new RectF();
    protected boolean bSizeChanged = true;

    private AABB bb = new AABB();       //Bounding Box

    public Sprite() {

    }

    public void setSize(Vector2Di newSize) {
        m_viSize = newSize;
        bScaleChanged = true;
    }

    public void setSize(int width, int height) {
        m_viSize.x = width;
        m_viSize.y = height;
        bSizeChanged = true;
    }

    public void setTexture(Bitmap bitmapIn) {
        m_SpritesheetBMP = m_SpritesheetBMP.createScaledBitmap(bitmapIn,bitmapIn.getWidth(),bitmapIn.getHeight(),false);
        //m_SpritesheetBMP = bitmapIn;
        m_viSize.x = m_SpritesheetBMP.getWidth();
        m_viSize.y = m_SpritesheetBMP.getHeight();
        setOrigin(m_viSize.x / 2, m_viSize.y / 2);
        src = new Rect(0,0,m_viSize.x,m_viSize.y);
        dst = new RectF(this.getPosition().x,this.getPosition().y,
                this.getPosition().x + (float)m_viSize.x,this.getPosition ().y + (float)m_viSize.y);
        m_bHasTexture = true;
    }

    public Vector2Di getSize() {
        return m_viSize;
    }

    public boolean collision(AABB other) {
        return bb.collision(other);
    }

    public boolean collision(CircleShape other) {
        return bb.collision(other);
    }

    public AABB getBB() {
        return bb;
    }

    public boolean impulseCollision(Sprite other, boolean immovable) {
        Vector2D vNormal = (this.getPosition().subtract(other.getPosition())).unitVector();
        this.setVelocity(this.getVelocity().add((vNormal.multiply(vNormal)).dot(this.getVelocity().multiply(-(1.f + e)))));
        if (immovable) {

        } else {
            Vector2D vVelDiff = this.getVelocity().subtract(other.getVelocity());
        }
        return false;
    }

    public void draw (Paint p,Canvas c) {
        if (m_bHasTexture) {
            if (bPositionChanged || bOriginChanged || bScaleChanged || bSizeChanged) {
                dst = new RectF(this.getPosition().x - this.getOrigin().x,this.getPosition().y - this.getOrigin().y,
                        (this.getPosition().x + m_viSize.x * this.getScale().x) - this.getOrigin().x,
                        (this.getPosition ().y + m_viSize.y * this.getScale().x) - this.getOrigin().y);

                bb.setOrigin(this.getOrigin());
                bb.setPosition(this.getPosition());
                bb.setSize(src.width() *(int)this.getScale().x,src.height() * (int)this.getScale().y);

                this.bPositionChanged = false;
                this.bRotationChanged = false;
                this.bScaleChanged = false;
                this.bSizeChanged = false;
            }
            bb.setPosition(this.getPosition());
            bb.draw(p,c);
            c.rotate(getRotation(), this.getPosition().x, this.getPosition().y);
            c.drawBitmap(m_SpritesheetBMP, src, dst, p);
            c.rotate(-getRotation(), this.getPosition().x, this.getPosition().y);

        }
    }
}