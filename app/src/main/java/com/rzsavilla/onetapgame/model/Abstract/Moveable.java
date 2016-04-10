package com.rzsavilla.onetapgame.model.Abstract;

import com.rzsavilla.onetapgame.model.Utilites.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public abstract class Moveable extends Transformable {
    private Vector2D m_vVelocity = new Vector2D();
    private float m_fForce = 0.0f;
    private float m_fMass = 0.0f;

    public void setVelocity(Vector2D newVelocity) {
        m_vVelocity = newVelocity;
    }

    public  void setVelocity(float x, float y) {
        m_vVelocity.x = x;
        m_vVelocity.y = y;
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

    public float getAcceleration() {
        return m_fForce / m_fMass;
    }

    public void moveUpdate(float timeStep) {
        setPosition(this.getPosition().add(m_vVelocity.multiply(timeStep)));
    }
}