package com.rzsavilla.onetapgame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

import java.text.FieldPosition;

/**
 * Created by rzsavilla on 15/03/2016.
 */
public class Sprite extends Transformable {
    private Bitmap m_SpritesheetBMP;
    private Vector2D m_vSize;
    private Rect m_TextureRect = new Rect();
    private Point frameSize = new Point(200,200);
    private Point frameCount = new Point(2,2);

    public void setSpriteSheet(Bitmap bitmapIn) {
        m_vSize = new Vector2D(200,200);
        m_SpritesheetBMP = m_SpritesheetBMP.createScaledBitmap(bitmapIn,frameSize.x * frameCount.x,frameSize.y * frameCount.y, false);
        m_TextureRect = new Rect(50,50,100,100);
        setOrigin(frameSize.x / 2, frameSize.y / 2);
    }

    public void setTextureRect(Rect newRect) {
        m_TextureRect = newRect;
    }

    public void drawSprite (Paint p,Canvas c) {
        setPosition(200.0f, 200.0f);
        //setRotatation(getRotation() + 2.1f);
        c.rotate(getRotation(),getPosition().x, getPosition().y);

        Rect src = new Rect(0,0,frameSize.x*2,frameSize.y);

        Vector2D top = new Vector2D(this.getPosition().subtract(this.getOrigin()));
        Vector2D bot = new Vector2D((this.getPosition().add(m_vSize)).subtract(this.getOrigin()));
        RectF dst = new RectF(top.x,top.y,bot.x,bot.y);
        c.drawBitmap(m_SpritesheetBMP,src,dst,p);
        c.rotate(-getRotation());
    }
}
