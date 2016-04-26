package com.rzsavilla.onetapgame.Sprite;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.rzsavilla.onetapgame.model.Utilites.Elapsed;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

import java.io.Serializable;

/**
 * Created by rzsavilla on 16/03/2016.
 */
public class AnimatedSprite extends Sprite implements Cloneable{

    //Animation
    private Elapsed timer = new Elapsed();
    private boolean m_bPlayAnimation = false;
    private boolean m_bLoopAnimation = false;
    private float m_fAnimatationSpeed = 0.2f;
    private Vector2Di m_vFrameSize = new Vector2Di(100,100);
    private Vector2Di m_vFrameCount = new Vector2Di(0,0);

    private int m_iCurrFrame = 0;
    private int m_iFrameX = 0;
    private int m_iFrameY = 0;
    private int m_iFrameCount = 4;

    public void setSpriteSheet(Bitmap bitmapIn, Vector2Di FrameSize, Vector2Di frameCount) {
        m_Texture = m_Texture.createScaledBitmap(bitmapIn,FrameSize.x * frameCount.x, FrameSize.y * frameCount.y,false);

        m_vFrameCount = frameCount;
        m_vFrameSize = FrameSize;
        setWidth(FrameSize.x);
        setHeight(FrameSize.y);
        setOrigin(getWidth() / 2, getHeight() / 2);
        src = new Rect(0,0,FrameSize.x,FrameSize.y);
        m_bHasTexture = true;
    }

    public void setAnimatedSprite(AnimatedSprite copy) {
        if (copy.m_bHasTexture) {
            this.m_Texture = copy.m_Texture;
            this.m_bHasTexture = true;
            this.m_vFrameCount= copy.m_vFrameCount;
            this.m_vFrameSize = copy.m_vFrameSize;
            this.m_bLoopAnimation = copy.m_bLoopAnimation;
            this.m_iCurrFrame = copy.m_iCurrFrame;
            this.m_iFrameX = copy.m_iFrameX;
            this.m_iFrameY = copy.m_iFrameY;
            this.src = copy.src;
            this.dst = copy.dst;
        }
        this.setLoop(copy.m_bLoopAnimation);
        this.setAnimationSpeed(copy.m_fAnimatationSpeed);
    }

    public void setAnimationSpeed(float speed) {
        m_fAnimatationSpeed = speed;
    }
    public void setLoop(boolean loop) { m_bLoopAnimation = loop; }

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

    public Vector2Di getFrameSize() { return m_vFrameSize; }
    public Vector2Di getFrameCount() { return m_vFrameCount; }
    public float getAnimationSpeed() { return m_fAnimatationSpeed; }

    private boolean isAnimPlaying() {           //Return if animation is playing
        return m_bPlayAnimation;
    }

    private boolean isAnimLooping() {           //Return if animation will loop
        return m_bLoopAnimation;
    }

    protected void updateAnimation() {
        if (this.m_bHasTexture) {
            if (timer.getElapsed() >= m_fAnimatationSpeed) {        //Check when frame changes
                if ((m_iCurrFrame + 1) < m_iFrameCount) {
                    //Next Frame
                    if ((m_iFrameX + 1) * m_vFrameSize.x >= m_Texture.getWidth()) {
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

    public AnimatedSprite clone() throws CloneNotSupportedException {
        AnimatedSprite newASprite = (AnimatedSprite) super.clone();

        return newASprite;
    }
}
