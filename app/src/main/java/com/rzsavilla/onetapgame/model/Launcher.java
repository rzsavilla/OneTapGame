package com.rzsavilla.onetapgame.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Collision.Circle;
import com.rzsavilla.onetapgame.model.Inherited.Sprite;
import com.rzsavilla.onetapgame.model.Projectiles.Projectile;
import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
import com.rzsavilla.onetapgame.model.Utilites.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * A Cannon/Gun/Crossbow/Catapult/etc
 * Will shoot projectiles
 */
public class Launcher extends Transformable {
    /**
     * Speed of Launcher Rotation
     */
    private float m_fRotationSpeed = 50.0f;
    private boolean m_bRotateLeft = false;
    private boolean m_bRotateRight = false;
    private boolean m_bHasTarget = false;
    /**
     * Cannon will iterate through the array of positions rotate and shoot towards those positions
     */
    private ArrayList<Vector2D> m_vTargetPos = new ArrayList<>();
    private Vector2D m_vCurretTarget = new Vector2D();
    private CircleShape m_TargetMarker = new CircleShape(0.0f,0.0f,10.0f, Color.BLUE);
    private ArrayList<CircleShape> m_aMarkers = new ArrayList<>();

    public ProjectileHandler m_Bullets = new ProjectileHandler();
    public Sprite sprite;   //The cannon

    /**
     * Default constructor
     */
    public Launcher() {
        sprite = new Sprite();
        setRotatation(1.0f);
    }

    /**
     * Constructor that sets Launcher position size and colour
     * @param position Position of the Launcher
     * @param size     Size of Launcher
     */
    public Launcher(Vector2D position, Vector2D size) {
        setPosition(position);
        setSize(size);
    }

    /**
     * Draw the Launcher and projectiles
     * @param p Paint
     * @param c Canvas to draw on.
     */
    public void draw(Paint p, Canvas c) {
        for (CircleShape marker : m_aMarkers) {
            marker.draw(p,c);
        }
        m_Bullets.drawProj(p, c);
        sprite.setPosition(this.getPosition().x, this.getPosition().y);
        sprite.draw(p, c);
    }

    /**
     * Rotate Launcher towards position
     * @param target position to rotate towards
     * @return true if desired rotation has been achieved
     */
    public boolean rotateTowards(Vector2D target) {
        //Calculate Gradient
        float x = this.getPosition().x - target.x;              //Position difference
        float y = this.getPosition().y - target.y;
        float g = (float)Math.atan2((double)y,(double)x);       //Angle
        g = g * (180 / (float) Math.PI) - 90;                   //Target angle
        //this.setRotatation((g * (180 / (float) Math.PI) - 90)/ m_fRotationSpeed);
        //Rotation difference
        System.out.println((g - getRotation()));
       if (Math.abs(g - getRotation()) <= m_fRotationSpeed * 0.2f) {
           //Target rotation has been achieved
           return true;
       }
        if  (g > getRotation()) { m_bRotateRight = true; }
        else if (g < getRotation()){ m_bRotateLeft = true; }
        return false;
    }

    public void markTarget(Vector2D target) {
        m_vTargetPos.add(target);
        m_aMarkers.add(new CircleShape(target.x ,target.y, 50.0f, Color.BLUE));
        //System.out.println(m_vTargetPos.size());
        //System.out.println("Target Added");
    }

    /**
     * Update Launcher movement and projectiles.
     * @param timeStep
     */
    public void update(float timeStep) {
        //Update Sprite and bullet spawn positions
        if (bPositionChanged) {
            sprite.setPosition(this.getPosition());
            m_Bullets.setPosition(this.getPosition());
            bPositionChanged = false;
        }
        //Update Sprite origin
        if (bOriginChanged) {
            sprite.setOrigin(this.getOrigin());
            bOriginChanged = false;
        }
        //Update Sprite rotation
        if (bRotationChanged) {
            sprite.setRotatation(this.getRotation());
            bRotationChanged = false;
        }

        //Set Target
        if (!m_vTargetPos.isEmpty() && !m_bHasTarget) {
            ListIterator<Vector2D> itr = m_vTargetPos.listIterator();
            if(itr.hasNext()) {                    //Iterate through positions
                m_vCurretTarget = itr.next();      //Get element and set current target as element
                //System.out.println(" Target Set");
                itr.remove();                      //Remove the position
            }
            m_bHasTarget = true; //flag Launcher will rotate towards target
        }

        if (m_bHasTarget) {
            //Set Desired rotation
            if (rotateTowards(m_vCurretTarget)) {
                //Desired rotation has been achieved
                //System.out.println("  Target Achieved");
                if (m_Bullets.canShoot()) {
                    m_Bullets.shoot(m_vCurretTarget);   //Shoot
                    m_aMarkers.remove(m_aMarkers.iterator().next());
                    m_bHasTarget = false; //flag target reached, set new target
                }
            }
            //Rotate towards target
            if (m_bRotateRight) {
                setRotatation(getRotation() + m_fRotationSpeed * timeStep);
                m_bRotateRight = false;
            }
            else if (m_bRotateLeft) {
                setRotatation(getRotation() - m_fRotationSpeed * timeStep);
                m_bRotateLeft = false;
            }
        }

        //System.out.println(m_bHasTarget);
        m_Bullets.update(timeStep);
    }
}