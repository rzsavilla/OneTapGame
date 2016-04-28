package com.rzsavilla.onetapgame.model.Handler;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Utilites.Elapsed;
import com.rzsavilla.onetapgame.model.Projectiles.Projectile;
import com.rzsavilla.onetapgame.model.Abstract.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Bullet Spawner
 * @author rzsavilla
 */
public class ProjectileHandler extends Transformable {
    private Projectile m_Projectile = new Projectile();                 //Projectile to be fired
    public ArrayList<Projectile> m_aProjectiles = new ArrayList(0);     //Stores projectiles fired

    private float m_fRateOfFire = 0.5f;                                 //Limit Number of bullets fired in seconds
    private Elapsed timer = new Elapsed();                              //Timer for rate of fire, Checks for elapsed time before projectile can be fired again
    private boolean m_bCanShoot = false;                                //Flag to determine if bullets can be fired
    private boolean m_bShoot = false;                                   //Flag tells spawner on update to shoot projectile
    private Vector2D m_vTarget = new Vector2D();                        //Spawner shoot projectile towards this position

    /**
     *  Default Constructor
     */
    public ProjectileHandler() {
        setPosition(0.0f, 0.f);
        timer.restart();
    }

    /**
     * Set when delay before a projectile can be shot
     * @param seconds Set new rate of fire
     */
    public void setRateOfFire(float seconds) {
        m_fRateOfFire = seconds;
    }

    /**
     * Return rate of fire
     * @return Rate of Fire
     */
    public float getRateOfFire() {
        return m_fRateOfFire;
    }

    /**
     * If projectile handler is allowed to shoot
     * @return True if projectile handler can shoot a projectile
     */
    public boolean canShoot() { return m_bCanShoot; }

    /**
     * Projectile will shoot projectile towards a target position
     * @param target Position for shell to move towards
     */
    public void shoot(Vector2D target) {
        m_bShoot = true;
        m_vTarget = target;
    }

    /**
     * Projectile will shoot projectile towards a target position
     * @param x Position X for target to move towards
     * @param y  Position Y for target to move towards
     */
    public void shoot(float x, float y) {
        m_bShoot = true;
        m_vTarget.x = x;
        m_vTarget.y = y;
    }

    /**
     * Draw all of the projectiles
     * @param p
     * @param c
     */
    public void drawProj(Paint p, Canvas c) {
        if (!m_aProjectiles.isEmpty()) {             //Check if iteration is required
            for (Projectile proj : m_aProjectiles) { //Iterate through ArrayList
                proj.draw(p,c);                         //Draw
            }
        }
    }

    /**
     * Update all of the projectiles
     * @param timeStep Time step for euler integration
     */
    public void update(float timeStep) {
        //Update rate of fire
        if (!m_bCanShoot && timer.getElapsed() > m_fRateOfFire) { m_bCanShoot = true; }

        //Shoot projectile
        if (m_bShoot && m_bCanShoot) {
            m_aProjectiles.add(new Projectile(this.getPosition().x, this.getPosition().y, m_vTarget.x, m_vTarget.y));
            m_bCanShoot = false;    //Reset flags
            m_bShoot = false;
            timer.restart();    //Rate of fire
        }

        //Update all projectiles
        for (Projectile proj: m_aProjectiles) {
            proj.update(timeStep);
        }
        //Check for destroyed projectiles
        ListIterator<Projectile> itr = m_aProjectiles.listIterator();
        while(itr.hasNext()) {                      //Iterate through bullets
            if (itr.next().isDestroyed()) {
                itr.remove();
            }
        }
    }
}
