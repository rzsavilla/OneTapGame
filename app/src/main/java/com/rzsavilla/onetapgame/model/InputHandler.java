package com.rzsavilla.onetapgame.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.jar.Attributes;

/**
 * Created by rzsavilla on 15/03/2016.
 */
public class InputHandler {
    private boolean m_bClick = false;                     //Player has tapped the screen
    private Vector2D m_vTapPos = new Vector2D();          //Position of player screen tap
    private MotionEvent m_MotionEvent;
    private boolean m_bUpdateEvent = false;

    public void InputHandler() {

    }

    public void setUpdate(boolean update) {
        m_bUpdateEvent = update;
    }

    public void setEvent(MotionEvent event) {
        m_MotionEvent = event;
        m_bUpdateEvent = true;
    }

    public Vector2D getTapPos() {
        return m_vTapPos;
    }

    public boolean isDown() {
        return m_bClick;
    }

    public boolean update() {
        if (m_bUpdateEvent) {
            int iAction = m_MotionEvent.getAction();
            switch (iAction) {
                case MotionEvent.ACTION_DOWN:
                    // finger touches the screen
                    m_vTapPos.x = m_MotionEvent.getX();
                    m_vTapPos.y = m_MotionEvent.getY();
                    m_bClick = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    // finger moves on the screen
                    m_vTapPos.x = m_MotionEvent.getX();
                    m_vTapPos.y = m_MotionEvent.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    // finger leaves the screen
                    m_bClick = false;
                    break;
            }
            m_bUpdateEvent = false;         //Event has been updated
        }
        return true;
    }
}
