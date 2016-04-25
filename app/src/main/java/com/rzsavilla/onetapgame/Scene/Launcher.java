package com.rzsavilla.onetapgame.Scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.Sprite.Sprite;
import com.rzsavilla.onetapgame.model.Utilites.Elapsed;
import com.rzsavilla.onetapgame.model.Handler.ProjectileHandler;
import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
import com.rzsavilla.onetapgame.model.Utilites.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A Cannon/Gun/Crossbow/Catapult/etc
 * Will shoot projectiles
 * @author rzsavilla
 */
public class Launcher extends Transformable implements Cloneable {
    /** Speed of Launcher Rotation */
    private float m_fRotationSpeed = 20.0f;
    private boolean m_bRotateLeft = false;
    private boolean m_bRotateRight = false;

    /** Projectile Spawner */
    public ProjectileHandler m_Bullets = new ProjectileHandler();
    /** Cannon will iterate through the queue of target rotate and shoot towards those positions */
    private Queue<CircleShape> m_Targets = new LinkedList<CircleShape>();
    /** Launchers current target position to rotate towards and shoot */
    private Vector2D m_vCurretTarget = new Vector2D();
    private boolean m_bHasTarget = false;

    /** The Launcher Sprite */
    public Sprite m_Sprite = new Sprite();
    /** Array of markers that show cannons target */
    /** Maximum number of target markers that can be placed */
    private Elapsed m_Timer = new Elapsed();     //Checks marker delay

    /**
     * Default constructor
     */
    public Launcher() {
        m_Timer.restart();
    }

    /**
     * Create new launcher by copying a launchers values
     * @param launcher
     */
    public Launcher(Launcher launcher) {
        setRotatation(launcher.getRotation());
        setSize(launcher.getSize());
        setBounds(launcher.getBounds());
        setScale(launcher.getScale());
        setOrigin(launcher.getOrigin());
        setSprite(launcher.getSprite());
        setPosition(launcher.getPosition());
    }

    /**
     * Constructor sets Launcher position size and sprite texture
     * @param position
     * @param size
     * @param texture
     */
    public Launcher(Vector2D position, Vector2D size, Bitmap texture) {
        setPosition(position);
        setSize(size);
        setOrigin(size.divide(2));
        m_Sprite = new Sprite();
        m_Sprite.setTexture(texture);
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
        //System.out.println((g - getRotation()));
       if (Math.abs(g - getRotation()) <= m_fRotationSpeed * 0.2f) {
           //Target rotation has been achieved
           return true;
       }
        if  (g > getRotation()) { m_bRotateRight = true; }
        else if (g < getRotation()){ m_bRotateLeft = true; }
        return false;
    }

    /**
     * Add a target position for the Launcher to shoot at
     * @param target Position to be added to array of target positions
     */
    public void markTarget(Vector2D target) {
        if ((m_Timer.getElapsed() > 0.1f) && m_Targets.size() < 3 ) {
            m_Targets.add(new CircleShape(target.x, target.y, 50.0f, Color.BLUE));
            System.out.println(m_Targets.size());
            m_Timer.restart();
        }
    }

    public void setSprite(Sprite sprite) { m_Sprite = sprite; }
    public void setSpriteTexture(Bitmap texture) { m_Sprite.setTexture(texture); }
    public Sprite getSprite() { return m_Sprite; }

    /**
     * Draw the Launcher ,projectiles and target markers
     * @param p Paint
     * @param c Canvas to draw on.
     */
    public void draw(Paint p, Canvas c) {
        for (CircleShape targets : m_Targets) {
            targets.draw(p,c);
        }
        m_Sprite.setPosition(this.getPosition().x, this.getPosition().y);
        m_Sprite.setRotatation(this.getRotation());
        m_Sprite.setOrigin(this.getOrigin());
        m_Bullets.setPosition(this.getPosition());
        m_Sprite.draw(p, c);
        m_Bullets.drawProj(p, c);
    }

    /**
     * Update Launcher movement and projectiles.
     * @param timeStep
     */
    public void update(float timeStep) {
        //Set Target
        if (!m_Targets.isEmpty() && !m_bHasTarget) {
                m_vCurretTarget = m_Targets.element().getPosition();      //Get element and set current target as element
                m_bHasTarget = true; //flag Launcher will rotate towards target
        }

        //Rotate towards target position and shoot
        if (m_bHasTarget) {
            //Set Desired rotation
            if (rotateTowards(m_vCurretTarget)) {
                //Desired rotation has been achieved
                if (m_Bullets.canShoot()) {
                    m_Bullets.shoot(m_vCurretTarget);   //Shoot
                    m_Targets.remove();                 //Remove front element
                    m_bHasTarget = false; //flag target reached, can set new target
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