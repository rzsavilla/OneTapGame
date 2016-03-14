package com.rzsavilla.onetapgame.model;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public class Moveable extends Transformable {
    private Vector2D m_vVelocity;
    private float m_fForce;
    private float m_fMass;

    public void setVelocity(Vector2D newVelocity) {
        m_vVelocity = newVelocity;
    }

    public void setForce(float newForce) {
        m_fForce = newForce;
    }

    public void setMass(float newMass) {
        m_fMass = newMass;
    }

    public Vector2D getVelocity() {
        return m_vVelocity;
    }

    public float getForce() {
        return m_fForce;
    }

    public float getMass() {
        return m_fMass;
    }
}
