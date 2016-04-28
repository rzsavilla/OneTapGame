package com.rzsavilla.onetapgame.Sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.rzsavilla.onetapgame.model.Abstract.Moveable;
import com.rzsavilla.onetapgame.model.Interface.Drawable;

import java.io.Serializable;

/**
 * Created by rzsavilla on 15/03/2016.
 */
public class Sprite extends Moveable {
    protected Bitmap m_Texture;
    protected boolean m_bHasTexture = false;
    private float e = 1;

    protected Rect src = new Rect();
    protected RectF dst = new RectF();
    protected boolean bSizeChanged = true;

    /**
     * Default constructor
     */
    public Sprite() {

    }

    /**
     * Set sprite texture
     * @param bitmapIn
     */
    public void setTexture(Bitmap bitmapIn) {
        m_Texture = m_Texture.createScaledBitmap(bitmapIn,bitmapIn.getWidth(),bitmapIn.getHeight(),false);
        setWidth(m_Texture.getWidth());
        setHeight(m_Texture.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        src = new Rect(0,0,(int)getWidth(),(int)getHeight());
        dst = new RectF(this.getPosition().x,this.getPosition().y,
                this.getPosition().x + getWidth(),this.getPosition ().y + getHeight());
        m_bHasTexture = true;
    }

    /**
     * Returns sprite texture
     * @return
     */
    public Bitmap getTexture() { return m_Texture; }

    /**
     * Draw sprite
     * @param p
     * @param c
     */
    public void draw (Paint p,Canvas c) {
        if (m_bHasTexture) {
            if (bPositionChanged || bSizeChanged || bScaleChanged || bOriginChanged) {
                updateGlobalBounds();
            }
            c.rotate(getRotation(), this.getPosition().x, this.getPosition().y);
            c.drawBitmap(m_Texture, src, getBounds(), p);
            c.rotate(-getRotation(), this.getPosition().x, this.getPosition().y);
        }
    }
}