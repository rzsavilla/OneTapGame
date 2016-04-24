package com.rzsavilla.onetapgame.model;

import com.rzsavilla.onetapgame.model.Inherited.Entity;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Lanes will spawn enemies, three lanes in total
 * Left, Center and Right lanes.
 * Controls stores and updates enemies
 */
public class Lane {
    private Vector2D m_vPosition = new Vector2D();      //Lane position (top left)
    private int m_iWidth;                               //width of the screen
    private boolean m_bOnLane = false;                  //Player is viewing this lane
    private ArrayList<Entity> m_aEnemies;               //Array of enemies

    /**Default Constructor */
    public Lane() {}
    /**Constructor that sets lanes position and width */
    public Lane(Vector2D position, int width) {
        m_vPosition = position;
        m_iWidth = width;
    }
    /**Constructor that sets lanes position and width */
    public Lane(float x,float y, int width) {
        m_iWidth = width;
        m_vPosition.x = x;
        m_vPosition.y = y;
    }

    /**Update all enemies on the lane*/
    public void update(float timeStep) {
        ListIterator<Entity> itr = m_aEnemies.listIterator();
        while(itr.hasNext()) {                      //Iterate through bullets
            Entity element = itr.next();            //Get enemy in array
            element.update(timeStep);               //Update enemy
            if (element.isDestroyed()) {            //Remove enemy from array
                itr.remove();
            }
        }
    }
}