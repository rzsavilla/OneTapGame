package com.rzsavilla.onetapgame.model.Inherited;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.rzsavilla.onetapgame.model.Abstract.Moveable;
import com.rzsavilla.onetapgame.model.Drawable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

/**
 * Created by rzsavilla on 15/03/2016.
 */
public class Sprite extends Moveable implements Drawable {
    protected Bitmap m_SpritesheetBMP;
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

    public void setTexture(Bitmap bitmapIn) {
        m_SpritesheetBMP = m_SpritesheetBMP.createScaledBitmap(bitmapIn,bitmapIn.getWidth(),bitmapIn.getHeight(),false);
        //m_SpritesheetBMP = bitmapIn;
        setWidth(m_SpritesheetBMP.getWidth());
        setHeight(m_SpritesheetBMP.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        src = new Rect(0,0,(int)getWidth(),(int)getHeight());
        dst = new RectF(this.getPosition().x,this.getPosition().y,
                this.getPosition().x + getWidth(),this.getPosition ().y + getHeight());
        m_bHasTexture = true;
    }

    public void draw (Paint p,Canvas c) {
        if (m_bHasTexture) {
            if (bPositionChanged || bSizeChanged || bScaleChanged || bOriginChanged) {
                updateGlobalBounds();
            }
            c.rotate(getRotation(), this.getPosition().x, this.getPosition().y);
            c.drawBitmap(m_SpritesheetBMP, src, getBounds(), p);
            c.rotate(-getRotation(), this.getPosition().x, this.getPosition().y);
        }
    }
}