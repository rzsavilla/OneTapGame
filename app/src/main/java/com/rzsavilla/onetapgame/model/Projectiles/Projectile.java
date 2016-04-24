package com.rzsavilla.onetapgame.model.Projectiles;

import android.graphics.Color;

import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public class Projectile extends CircleShape {
    private Vector2D m_vTargetPos = new Vector2D();
    private boolean m_bHasTarget = false;
    private boolean m_bReachedTarget = false;

    public Projectile() {
        super(0.0f,0.0f,20.0f, Color.RED);
        this.setForce(0.0f);
        this.setMass(1.0f);
    }

    public Projectile(float xPos, float yPos, float targetX, float targetY) {
        super(xPos ,yPos,20.0f, Color.RED);
        setForce(1000.0f);
        setTarget(targetX,targetY);
    }

    public Projectile(Vector2D position, Vector2D targetPos) {
        setPosition(position);
        setTarget(targetPos);
    }

    public  void setTarget(Vector2D targetPos) {
        setTarget(targetPos.x,targetPos.y);
    }

    public void setTarget(float x, float y) {
        m_vTargetPos.x = x;
        m_vTargetPos.y = y;
        m_bHasTarget = true;
        m_bReachedTarget = false;

        Vector2D vDiff = m_vTargetPos.subtract(this.getPosition());
        float fDistance = vDiff.magnitude();
        this.setVelocity((m_vTargetPos.x - this.getPosition().x) / fDistance,
                (m_vTargetPos.y - this.getPosition().y) / fDistance);
        m_bHasTarget = true;
    }

    public void update(float timeStep) {
        //Check if projectile has reached target
        if (m_bHasTarget) {
            Vector2D vDiff = m_vTargetPos.subtract(this.getPosition());
            float fDistance = vDiff.magnitude();
            if (fDistance < this.getAcceleration() * timeStep) {
                m_bReachedTarget = true;
                m_bHasTarget = false;
                this.setVelocity(0.0f, 0.0f);
            }
            if (m_bReachedTarget) {
                destroy();
            }
            moveUpdate((timeStep));
        }
    }
}
