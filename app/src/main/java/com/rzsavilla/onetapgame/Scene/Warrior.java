package com.rzsavilla.onetapgame.Scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.Sprite.Enemy.Entity;
import com.rzsavilla.onetapgame.Sprite.Sprite;
import com.rzsavilla.onetapgame.model.Handler.ProjectileHandler;
import com.rzsavilla.onetapgame.model.Utilites.Elapsed;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Created by rzsavilla on 25/04/2016.
 */
public class Warrior extends Enemy{
    private Sprite m_Weapon = new Sprite();
    private boolean m_bHasWeapon;
    private boolean m_bShowWeapon = false;
    /**
     * Default Constructor
     */
    private Elapsed m_Timer = new Elapsed();

    public Warrior() {
        setForce(300.0f);
        setMass(10.0f);
        setHitRate(2.0f);
        setValue(3);
    }

    public void setWeapon(Bitmap texture) {
        m_Weapon.setTexture(texture);
        m_bHasWeapon = true;
    }

    public void update(float timeStep) {

        if (bPositionChanged) {
            //m_Weapon.setPosition(this.getPosition());
        }
        if (m_bAttacking && !m_bShowWeapon) {
            this.m_bShowWeapon = true;
            this.m_Weapon.setPosition(this.getPosition().x,this.getPosition().y);
            this.m_Timer.restart();
        }

        if (m_bShowWeapon) {
            if (m_Timer.getElapsed() < this.getHitRate()) {
                float x = this.getPosition().x - this.getWidth() / 2.3f;
                this.m_Weapon.setPosition(x,m_Weapon.getPosition().y + 200.0f * timeStep);

            } else {
                m_bShowWeapon = false;
            }
        }

        super.update(timeStep);
    }

    public  void draw(Paint p, Canvas c) {
        super.draw(p, c);
        if (m_bShowWeapon && this.m_bHasWeapon) {
            m_Weapon.draw(p,c);
        }
    }
}
