package com.rzsavilla.onetapgame.model;

import android.view.MotionEvent;

import com.rzsavilla.onetapgame.model.Collision.AABB;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Created by rzsavilla on 15/03/2016.
 */
public class InputHandler {
    private boolean m_bClick = false;                     //Player has tapped the screen
    private Vector2D m_vTapPos = new Vector2D();          //Position of player screen tap
    private MotionEvent m_MotionEvent;
    private boolean m_bUpdateEvent = false;
    private Vector2D m_vRelativePosition = new Vector2D();                 //Position of canvas

    public AABB m_MouseBB = new AABB();

    public void InputHandler() {

    }

    public boolean bTap = false;

    public void setUpdate(boolean update) {
        m_bUpdateEvent = update;
    }

    public void setEvent(MotionEvent event) {
        m_MotionEvent = event;
        m_bUpdateEvent = true;
    }

    public void setEvent(MotionEvent event, Vector2D relative) {
        m_MotionEvent = event;
        m_vRelativePosition = relative;
        m_MouseBB.setPosition(m_vTapPos);
        m_MouseBB.setSize(50.0f,50.0f);
        m_MouseBB.updateGlobalBounds();
        m_bUpdateEvent = true;
    }

    public Vector2D getTapPos() {
        return m_vTapPos.add(m_vRelativePosition);
    }

    public AABB getMouseBB() { return m_MouseBB; }

    public boolean isDown() {
        return m_bClick;
    }

    public boolean update(Vector2D canvasPos) {
        m_vRelativePosition = canvasPos;
        if (m_bUpdateEvent) {
            int iAction = m_MotionEvent.getAction();
            switch (iAction) {
                case MotionEvent.ACTION_DOWN:
                    // finger touches the screen
                    m_vTapPos.x = m_MotionEvent.getX();
                    m_vTapPos.y = m_MotionEvent.getY();
                    m_MouseBB.setPosition(getTapPos());
                    m_bClick = true;
                    bTap = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    // finger moves on the screen
                    m_vTapPos.x = m_MotionEvent.getX();
                    m_vTapPos.y = m_MotionEvent.getY();
                    m_MouseBB.setPosition(getTapPos());
                    break;
                case MotionEvent.ACTION_UP:
                    // finger leaves the screen
                    m_bClick = false;
                    bTap = false;
                    m_MouseBB.setPosition(0.0f,0.0f);       //Reset Mouse position
                    break;
            }

            m_bUpdateEvent = false;         //Event has been updated
        }
        return true;
    }

    public void reset() {
        m_bClick = false;
    }
}