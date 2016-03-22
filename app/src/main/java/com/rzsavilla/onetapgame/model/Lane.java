package com.rzsavilla.onetapgame.model;

/**
 * Enemy Spawner
 */
public class Lane {
    private Vector2D m_vPosition = new Vector2D();
    private int m_iWidth;
    private boolean m_bOnLane = false;                  //Player is viewing this lane

    Lane(Vector2D position, int widht) {

    }

    public Lane(float x,float y, int width) {
        m_iWidth = width;
        m_vPosition.x = x;
        m_vPosition.y = y;
    }

}
