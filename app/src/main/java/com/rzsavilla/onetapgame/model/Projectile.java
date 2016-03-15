package com.rzsavilla.onetapgame.model;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public class Projectile extends CircleShape{
    private Vector2D m_vTargetPos = new Vector2D();
    private boolean m_bHasTarget = false;
    private boolean m_bReachedTarget = false;
    private double m_dRateOfFire = 0.1d;
    private float fSpeed;

    public Projectile() {
        super(0.0f,0.0f,20.0f, Color.RED);
        fSpeed = 20.0f;
    }

    public  void setTarget(Vector2D targetPos) {
        m_vTargetPos = targetPos;
        m_bHasTarget = true;
        m_bReachedTarget = false;
    }

    public void setTarget(float x, float y) {
        m_vTargetPos.x = x;
        m_vTargetPos.y = y;
        m_bHasTarget = true;
        m_bReachedTarget = false;
    }

    public void update() {
        if (m_bHasTarget) {
            //Will move towards target
            if (!m_bReachedTarget) {
                Vector2D vDiff;
                vDiff = m_vTargetPos.subtract(this.getPosition());
                float fDistance;
                fDistance = vDiff.magnitude();
                Log.d("Distance: ", Float.toString(fDistance));
                this.setVelocity(fSpeed * (m_vTargetPos.x - this.getPosition().x) / fDistance,
                        fSpeed * (m_vTargetPos.y - this.getPosition().y) / fDistance);
                if (fDistance > -10.0f && fDistance < 10.0f) {
                    m_bReachedTarget = true;
                    m_bHasTarget = false;
                    this.setVelocity(0.0f,0.0f);
                }
                System.out.println();
                //Move
                setPosition(this.getPosition().add(this.getVelocity()));
            }
        }
    }
}
