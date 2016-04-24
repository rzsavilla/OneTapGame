package com.rzsavilla.onetapgame.model.Handler;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Shapes.Collision.AABB;
import com.rzsavilla.onetapgame.model.Shapes.Collision.Circle;
import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Created by rzsavilla on 15/03/2016.
 */
public class InputHandler {

    /**
     *  Player is Pressing the screen if true
     */
    private boolean m_bTap;

    /**
     * Press position
     */
    private Circle m_TapPosition = new Circle();

    /**
     * Default Constructor initialize variables
     */
    public InputHandler() {
        m_TapPosition.setPosition(0.0f,0.0f);
        m_TapPosition.setRadius(25.0f);
        m_TapPosition.setColour(Color.GREEN);
        m_bTap = false;
    }

    /**
     * Checks if player is pressing down on the screen
     * @return True if player is pressing down on the screen
     */
    public boolean isDown() { return m_bTap; }

    /**
     * Check if player taps on an AABB
     * @param other
     * @return True if player has tapped on AABB
     */
    public boolean tap(AABB other) {
        if (m_TapPosition.collision(other)) { return  true; }
        else { return false; }
    }

    /**
     * Check if player taps on a Circle
     * @param other
     * @return True if player has tapped on Circle
     */
    public boolean tap(CircleShape other) {
        if (m_TapPosition.collision(other)) { return true; }
        else {return false; }
    }

    /**
     * Return position of tap on the screen
     * @return
     */
    public Vector2D getTapPos() { return m_TapPosition.getPosition(); }

    /**
     * Return position of tap relative to vector
     * @param relative Tap position relative to this vector, will likely be screen position
     * @return returns Tap position
     */
    public Vector2D getTapPos(Vector2D relative) { return m_TapPosition.getPosition().add(relative); }

    /**
     * Update current tap position and if player is pressing down onto the screen
     * @param position Position of the tap
     * @param isDown If tap is down
     */
    public void updateTap(Vector2D position, boolean isDown) {
        m_TapPosition.setPosition(position);
        m_bTap = isDown;
    }

    /**
     * Update tap relative to screen position
     * @param position
     * @param isDown
     * @param relativeScreen
     */
    public void updateTap(Vector2D position, boolean isDown, Vector2D relativeScreen) {
        m_TapPosition.setPosition(position.add(relativeScreen));
        m_bTap = isDown;
    }

    /**
     * Draw tap AABB
     * @param p
     * @param c
     */
    public void draw(Paint p, Canvas c) {
        m_TapPosition.draw(p,c);
    }
}