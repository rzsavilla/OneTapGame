package com.rzsavilla.onetapgame.Scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.Sprite.Enemy.Entity;
import com.rzsavilla.onetapgame.model.Handler.InputHandler;
import com.rzsavilla.onetapgame.model.Handler.TextureHandler;
import com.rzsavilla.onetapgame.model.Shapes.RectangleShape;
import com.rzsavilla.onetapgame.model.Utilites.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Lanes will spawn enemies, three lanes in total
 * Left, Center and Right lanes.
 * Controls stores and updates enemies
 */
public class Lane extends Transformable{
    private boolean m_bOnLane = false;                  //Player is viewing this lane
    private ArrayList<Entity> m_aEnemies = new ArrayList<>();       //Array of enemies
    private Launcher m_Launcher;
    private RectangleShape m_Wall = new RectangleShape();

    /**
     * Constructor
     * @param position
     * @param width
     * @param height
     * @param textures
     */
    public Lane(Vector2D position, int width, int height,TextureHandler textures) {
        setPosition(position);
        setSize(width, height);
        initialize(textures);
    }

    /**
     *
     * @param textures
     * @return
     */
    public boolean initialize(TextureHandler textures) {
        float fCentreOffset = getSize().x / 2.0f;
        float fHeight;
        m_Launcher = new Launcher();
        m_Launcher.m_Sprite.setTexture(textures.getTexture(0));
        fHeight = m_Launcher.m_Sprite.getHeight();
        m_Launcher.setOrigin(m_Launcher.m_Sprite.getWidth() / 2.0f,m_Launcher.m_Sprite.getHeight() / 2.0f);
        m_Launcher.setPosition(this.getPosition().add(new Vector2D(fCentreOffset, getSize().y - fHeight)));

        m_Wall.setSize(this.getWidth(), m_Launcher.m_Sprite.getHeight() * 2);
        m_Wall.setPosition(this.getPosition().x, this.getSize().y - m_Wall.getSize().y);

        m_Wall.setColour(Color.GRAY);
        return true;
    }

    /**
     * Set whether player is viewing this lane
     * @param onLane
     * @return
     */
    public void setOnLane(boolean onLane) { m_bOnLane = onLane; }

    /**
     * Update Lane objects
     * @param timeStep
     * @param input
     */
    public void update(float timeStep, InputHandler input, boolean changingLanes) {
        if (m_bOnLane && !changingLanes) {
            if (input.isDown() && input.getTapPos().y < 2000.0f){
                m_Launcher.markTarget(input.getTapPos());
            }
        }
        m_Launcher.update(timeStep);

        ListIterator<Entity> itr = m_aEnemies.listIterator();
        while(itr.hasNext()) {                      //Iterate through bullets
            Entity element = itr.next();            //Get enemy in array
            element.update(timeStep);               //Update enemy
            if (element.isDestroyed()) {            //Remove enemy from array
                itr.remove();
            }
        }
    }

    /**
     * Draw all objects on the lane
     * @param p
     * @param c
     */
    public void draw(Paint p, Canvas c) {
        m_Wall.draw(p,c);
        m_Launcher.draw(p,c);
        for (Entity enemy: m_aEnemies) { enemy.draw(p,c); }
    }
}