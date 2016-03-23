package com.rzsavilla.onetapgame.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.rzsavilla.onetapgame.R;

/**
 * A Cannon/Gun/Crossbow/Catapult/etc
 */
public class Launcher extends Transformable{
    private float m_fRotationSpeed = 1.0f;
    private boolean m_bRotateLeft = false;
    private boolean m_bRotateRight = false;

    public ProjectileHandler m_Bullets = new ProjectileHandler();
    public Sprite sprite;

    public Launcher() {
        sprite = new Sprite();
    }

    public Launcher(Vector2D position, Vector2D size, int colour) {

    }

    public void draw(Paint p, Canvas c) {
        m_Bullets.drawProj(p, c);
        sprite.setPosition(this.getPosition().x, this.getPosition().y);
        sprite.draw(p,c);
    }

    public void rotateTowards(Vector2D target) {
        //Calculate Gradient
        float x = this.getPosition().x - target.x;
        float y = this.getPosition().y - target.y;
        float g = (float)Math.atan2((double)y,(double)x);
        this.setRotatation(g * (180 / (float) Math.PI) - 90);
    }

    public void update(float timeStep) {
        if (bPositionChanged) {
            sprite.setPosition(this.getPosition());
            m_Bullets.setPosition(this.getPosition());
            bPositionChanged = false;
        }
        if (bOriginChanged) {
            sprite.setOrigin(this.getOrigin());
            bOriginChanged = false;
        }
        if (bRotationChanged) {
            sprite.setRotatation(this.getRotation());
            bRotationChanged = false;
        }

        if (m_bRotateLeft) {

        } else if (m_bRotateRight) {

        }
        m_Bullets.update(timeStep);
    }
}