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

    private ProjectileHandler m_ProjHandler;
    public Sprite cannon;



    public Launcher() {
        cannon = new Sprite();
    }

    public Launcher(Vector2D position, Vector2D size, int colour) {

    }

    public void draw(Paint p, Canvas c) {
        //m_ProjHandler.drawProj(p,c);
        cannon.drawSprite(p,c);
    }

    public void update() {
        cannon.setPosition(this.getPosition());
        if (m_bRotateLeft) {

        } else if (m_bRotateRight) {

        }
    }
}