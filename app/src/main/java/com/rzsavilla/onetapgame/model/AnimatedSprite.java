package com.rzsavilla.onetapgame.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by rzsavilla on 16/03/2016.
 */
public class AnimatedSprite extends Sprite {
    private Bitmap m_SpritesheetBMP;
    private Vector2Di m_viSize;

    //Animation
    private Elapsed timer = new Elapsed();
    private boolean m_bPlayAnimation = true;
    private boolean m_bLoopAnimation = false;
    private float m_fAnimatationSpeed = 0.5f;
    private Point frameSize = new Point(100,100);

    private int m_iCurrFrame = 0;
    private int m_iFrameX = 0;
    private int m_iFrameY = 0;
    private int m_iFrameCount = 0;

    private Rect src = new Rect();       //Texture rect, Position of frame on the bmp
    private RectF dst = new RectF();     //Position and size of the bmp;

    public void setSpriteSheet(Bitmap bitmapIn) {

        m_SpritesheetBMP = m_SpritesheetBMP.createScaledBitmap(bitmapIn,bitmapIn.getWidth(),bitmapIn.getHeight(), false);
        m_viSize = new Vector2Di(bitmapIn.getWidth(),bitmapIn.getHeight());
        frameSize = new Point(bitmapIn.getWidth(),bitmapIn.getHeight());
        setOrigin(frameSize.x / 2, frameSize.y / 2);
    }

    private void setSize(Vector2Di newSize) {
        m_viSize = newSize;
    }

    private void setSize(int width, int height) {
        m_viSize.x = width;
        m_viSize.y = height;
    }

    public void setAnimationSpeed(float speed) {
        m_fAnimatationSpeed = speed;
    }

    public void drawSprite (Paint p,Canvas c) {
        setPosition(200.0f, 200.0f);
        //setRotatation(getRotation() + 2.1f);

        updateAnimation();
        c.rotate(getRotation(),getPosition().x, getPosition().y);

        c.drawBitmap(m_SpritesheetBMP,src,dst,p);
        c.rotate(-getRotation());
    }

    public void resetAnimation() {              //Play animation from starting frame
        m_bPlayAnimation = true;
        m_iFrameX = 0;
        m_iFrameY = 0;
        m_iCurrFrame = 0;
    }

    public void playAnimation() {               //Play/Continue animation;
        m_bPlayAnimation = true;
    }

    public void stopAnimation() {               //Stop animation on current frame;
        m_bPlayAnimation = false;
    }

    public int getCurrFrame() {                 //Returns current animation frame
        return m_iCurrFrame;
    }

    private boolean isAnimPlaying() {           //Return if animation is playing
        return m_bPlayAnimation;
    }

    private boolean isAnimLooping() {           //Return if animation will loop
        return m_bLoopAnimation;
    }

    private void updateAnimation() {
        if (timer.getElapsed() >= m_fAnimatationSpeed) {        //Check when frame changes
            if ((m_iCurrFrame + 1) < m_iFrameCount) {
                //Next Frame
                if ((m_iFrameX + 1) * frameSize.x >= m_SpritesheetBMP.getWidth()) {
                    //Next frame is row down
                    m_iFrameX = 0;
                    m_iFrameY++;
                } else {
                    //Next Frame
                    m_iFrameX++;
                }
                m_iCurrFrame++;
            } else {
                //Animation has finished
                //Return to first frame
                m_iFrameX = 0;
                m_iFrameY = 0;
                m_iCurrFrame = 0;
                if (!m_bLoopAnimation) {      //Whether to start animation again
                    m_bPlayAnimation = false;       //Stop animation;
                }
            }
            //Update frame src
            src.left = frameSize.x * m_iFrameX;
            src.top = frameSize.y * m_iFrameY;
            src.right = src.left + frameSize.x;
            src.bottom = src.top + frameSize.y;
            timer.restart();
        }
    }

    public void update() {
        if (bPositionChanged) {
            Vector2D top = new Vector2D(this.getPosition().x - this.getOrigin().x,this.getPosition().y - this.getOrigin().y);
            Vector2D bot = new Vector2D(this.getPosition().x + (float)m_viSize.x - this.getOrigin().x, this.getPosition().y + (float)m_viSize.y - this.getOrigin().y);
            dst = new RectF(top.x,top.y,bot.x,bot.y);
            bPositionChanged = false;
        }
        if (m_bPlayAnimation) {
            updateAnimation();
        }
    }
}
