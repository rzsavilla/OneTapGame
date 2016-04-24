package com.rzsavilla.onetapgame.model.Abstract;

import com.rzsavilla.onetapgame.model.Utilites.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public abstract class Moveable extends Transformable {
    private Vector2D m_vVelocity = new Vector2D();          //Heading
    private Vector2D m_vCurrentVel = new Vector2D();        //Heading * Acceleration
    private float m_fForce = 10.0f;
    private float m_fMass = 1.0f;

    public void setVelocity(Vector2D newVelocity) {
        m_vVelocity = newVelocity;
    }

    public  void setVelocity(float x, float y) {
        m_vVelocity.x = x;
        m_vVelocity.y = y;
        m_vVelocity = m_vVelocity.unitVector();     //Normalize into heading
    }

    /**
     * Set force being applied onto the object
     * @param newForce New float Force.
     */
    public void setForce(float newForce) {
        m_fForce = newForce;
    }

    /**
     * Set objects mass
     * @param newMass new float Mass.
     */
    public void setMass(float newMass) {
        m_fMass = newMass;
    }

    public Vector2D getVelocity() {
        return m_vCurrentVel;
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
        m_vCurrentVel = m_vVelocity.multiply(getAcceleration() * timeStep);
        setPosition(this.getPosition().add(m_vCurrentVel.multiply(timeStep)));
    }
}