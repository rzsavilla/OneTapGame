package com.rzsavilla.onetapgame.model.Abstract;

import com.rzsavilla.onetapgame.Sprite.Sprite;
import com.rzsavilla.onetapgame.model.Utilites.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

import java.io.Serializable;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public abstract class Moveable extends Transformable{
    private Vector2D m_vVelocity = new Vector2D();          //Heading
    private Vector2D m_vCurrentVel = new Vector2D();        //Heading * Acceleration
    private float m_fForce = 200.0f;
    private float m_fMass = 10.0f;
    private Vector2D m_PrevPos = new Vector2D();

    /**
     * Set the objects heading
     * @param newVelocity
     */
    public void setVelocity(Vector2D newVelocity) { setVelocity(newVelocity.x,newVelocity.y); }

    /**
     * Set the objects heading
     * @param x
     * @param y
     */
    public  void setVelocity(float x, float y) {
        m_vVelocity.x = x;
        m_vVelocity.y = y;
        //m_vVelocity = m_vVelocity.unitVector();     //Normalize into heading
    }

    public void setVelX(float x) { m_vVelocity.x = x; }

    public void setVelY(float y) { m_vVelocity.y = y; }

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

    /**
     * Return objects current velocity
     * @return Vector velocity x,y
     */
    public Vector2D getVelocity() {
        return m_vVelocity;
    }

    /**
     * Returns force being applied to object
     * @return Float force applied to object
     */
    public float getForce() {
        return m_fForce;
    }

    /**
     * Returns Objects mass
     * @return Float objects mass
     */
    public float getMass() {
        return m_fMass;
    }

    /**
     * Returns objects Acceleration Acceleration = Force / Mass
     * @return Float acceleration of the object
     */
    public float getAcceleration() {
        return m_fForce / m_fMass;
    }

    public void moveBack() {
        setPosition(m_PrevPos);
    }

    /**
     * Move the object
     * @param timeStep
     */
    public void moveUpdate(float timeStep) {
        m_vCurrentVel = (m_vVelocity.multiply(getAcceleration()));
        m_PrevPos = this.getPosition();
        setPosition(this.getPosition().add(m_vCurrentVel.multiply(timeStep)));
    }
}