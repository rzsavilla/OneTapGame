package com.rzsavilla.onetapgame.model;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Utilites.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Bullet Spawner
 */
public class ProjectileHandler extends Transformable {
    private Projectile m_Projectile = new Projectile();                 //Projectile to be fired
    private ArrayList<Projectile> m_aProjectiles = new ArrayList();     //Stores projectiles fired

    public float m_fRateOfFire = 0.35f;                                 //Limit Number of bullets fired in seconds
    private Elapsed timer = new Elapsed();                              //Timer for rate of fire, Checks for elapsed time before projectile can be fired again
    private boolean m_bCanShoot = false;                                //Flag to determine if bullets can be fired
    private boolean m_bShoot = false;
    private Vector2D m_vTarget = new Vector2D();

    //Default Constructor
    //public ProjectileHandler() {
    //    setPosition(0.0f, 0.f);
    //    timer.restart();
    //}
//
    //public ProjectileHandler(Vector2D position) {
    //    this.setPosition(position);
    //}
//
    //public ProjectileHandler(float posX, float posY) {
    //    this.setPosition(posX, posY);
    //}

    public void setRateOfFire(float seconds) {
        m_fRateOfFire = seconds;
    }

    public float getRateOfFire() {
        return m_fRateOfFire;
    }

    //Shoot a bullet towards target
    public void shoot(Vector2D target) {
        m_bShoot = true;
        m_vTarget = target;
    }

    public void shoot(float x, float y) {
        m_bShoot = true;
        m_vTarget.x = x;
        m_vTarget.y = y;
    }

    //Draw all projectiles in the projectiles array
    protected void drawProj(Paint p, Canvas c) {
        if (!m_aProjectiles.isEmpty()) {             //Check if iteration is required
            for (Projectile proj : m_aProjectiles) { //Iterate through ArrayList
                proj.draw(p,c);                         //Draw
            }
        }
    }

    //Update all projectiles in array
    protected void update(float timeStep) {
        //Update rate of fire
        if (!m_bCanShoot) {
            if (timer.getElapsed() > m_fRateOfFire) {       //Check when projectiles can be shot
                timer.restart();
                m_bCanShoot = true;
            }
        }

        if (m_bShoot) {
            if (m_bCanShoot) {
                m_aProjectiles.add(new Projectile(this.getPosition().x, this.getPosition().y, m_vTarget.x, m_vTarget.y));
                m_bCanShoot = false;
            }
            m_bShoot = false;
        }

        //Update all projectiles
        if (!m_aProjectiles.isEmpty()) {
            ListIterator<Projectile> itr = m_aProjectiles.listIterator();
            while(itr.hasNext()) {                      //Iterate through bullets
                Projectile element = itr.next();        //Get element
                element.update(timeStep);               //Update projectile
                if (element.isDestroyed()) {
                    itr.remove();
                }
            }
        }
    }
}
