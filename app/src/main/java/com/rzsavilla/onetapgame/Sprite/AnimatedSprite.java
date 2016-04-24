package com.rzsavilla.onetapgame.Sprite;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.rzsavilla.onetapgame.model.Utilites.Elapsed;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

/**
 * Created by rzsavilla on 16/03/2016.
 */
public class AnimatedSprite extends Sprite {
    //private Bitmap m_SpritesheetBMP;
    //private Vector2Di m_viSize;

    //Animation
    private Elapsed timer = new Elapsed();
    private boolean m_bPlayAnimation = false;
    private boolean m_bLoopAnimation = false;
    private float m_fAnimatationSpeed = 0.2f;
    private Vector2Di m_vFrameSize = new Vector2Di(100,100);

    private int m_iCurrFrame = 0;
    private int m_iFrameX = 0;
    private int m_iFrameY = 0;
    private int m_iFrameCount = 4;

    public void setSpriteSheet(Bitmap bitmapIn, Vector2Di FrameSize, Vector2Di frameCount) {
        m_SpritesheetBMP = m_SpritesheetBMP.createScaledBitmap(bitmapIn,FrameSize.x * frameCount.x, FrameSize.y * frameCount.y,false);

        m_vFrameSize = FrameSize;
        setWidth(FrameSize.x);
        setHeight(FrameSize.y);
        setOrigin(getWidth() / 2, getHeight() / 2);
        src = new Rect(0,0,FrameSize.x,FrameSize.y);
        m_bHasTexture = true;
    }

    public void setAnimationSpeed(float speed) {
        m_fAnimatationSpeed = speed;
    }

    public void restartAnimation() {              //Play animation from starting frame
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

    protected void updateAnimation() {
        if (timer.getElapsed() >= m_fAnimatationSpeed) {        //Check when frame changes
            if ((m_iCurrFrame + 1) < m_iFrameCount) {
                //Next Frame
                if ((m_iFrameX + 1) * m_vFrameSize.x >= m_SpritesheetBMP.getWidth()) {
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
            src.left = m_vFrameSize.x * m_iFrameX;
            src.top = m_vFrameSize.y * m_iFrameY;
            src.right = src.left + m_vFrameSize.x;
            src.bottom = src.top + m_vFrameSize.y;
            //src = new Rect(0,0,this.getSize().x, this.getSize().y);
            timer.restart();
        }
    }
}
