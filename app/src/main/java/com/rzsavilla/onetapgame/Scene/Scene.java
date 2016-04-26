package com.rzsavilla.onetapgame.Scene;

import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.rzsavilla.onetapgame.R;
import com.rzsavilla.onetapgame.model.HUD.HUD;
import com.rzsavilla.onetapgame.model.Handler.InputHandler;
import com.rzsavilla.onetapgame.model.Handler.TextureHandler;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

import java.util.ArrayList;

/**
 * Created by rzsavilla on 24/04/2016.
 */
public class Scene {
    private boolean m_bPause = false; //In game pause
    private int m_iScreenWidth;
    private int m_iScreenHeight;
    Vector2D m_vCenter;
    Vector2D m_vLeft;
    Vector2D m_vRight;
    private Vector2D m_vScreenPos = new Vector2D();
    private ArrayList<Lane> m_aLanes = new ArrayList<>();
    private boolean m_bTextureLoaded = false;
    private TextureHandler m_Textures = new TextureHandler();
    private InputHandler m_Input = new InputHandler();
    private HUD hud = new HUD();

    private int m_iHealth = 100;
    private int m_iGold = 0;

    private int m_iWave = 1;
    private float m_fWaveDuration = 60.0f;


    //Lane Changing
    private boolean m_bChangeLane = false;

    /**
     * Default construtor
     */
    public Scene() {}

    /**
     * Load and initialize all objects and resources
     */
    public void setTextureHandler(TextureHandler handler) { m_Textures = handler; }
    public void initialize(int width, int height) {
        m_iScreenWidth = width;
        m_iScreenHeight = height;
        m_vCenter = new Vector2D(0.0f,0.0f);
        m_vLeft = new Vector2D((-m_iScreenWidth),0.0f);
        m_vRight = new Vector2D(m_iScreenWidth,0.0f);
        loadTextures();
        loadLanes();
        hud.initialize(m_iScreenWidth,m_iScreenHeight, m_Textures);
    }

    private boolean loadTextures() {
        if (!m_bTextureLoaded) {
            m_Textures.loadBitmap(R.drawable.cannon);
            m_Textures.loadBitmap(R.drawable.soldier_spritesheet);
            m_Textures.loadBitmap(R.drawable.grass);
            m_Textures.loadBitmap(R.drawable.warrior);
            m_Textures.loadBitmap(R.drawable.lava);
            m_bTextureLoaded = true;
        }
        return true;
    }

    /**
     * Create and place lanes
     * @return
     */
    Warrior baseWar = new Warrior();
    private boolean loadLanes() {
        //Add lanes into array

        baseWar.setHealth(10);
        baseWar.setPosition(500.0f, 800.0f);
        baseWar.setForce(50.0f);
        baseWar.setVelocity(0.0f, 0.0f);
        baseWar.setSpriteSheet(m_Textures.getTexture(1), new Vector2Di(250, 250), new Vector2Di(4, 1));
        m_aLanes.add(new Lane(m_vLeft, m_iScreenWidth, m_iScreenHeight, m_Textures));
        m_aLanes.add(new Lane(m_vCenter, m_iScreenWidth, m_iScreenHeight, m_Textures));
        m_aLanes.add(new Lane(m_vRight, m_iScreenWidth, m_iScreenHeight, m_Textures));
        m_aLanes.get(1).setOnLane(true);

        return true;
    }

    private float m_fScreenTargetX = 0.0f;
    private boolean m_bLaneMoveLeft = false; //true = left, false = right

    /**
     * Check whether player the lane to the left
     */
    private boolean moveLaneLeft() {
        if (m_vScreenPos.x > m_vLeft.x) {               //Check if on left lane
            //Check if on center lane
            if (m_vScreenPos.x <= m_vCenter.x) { m_fScreenTargetX = m_vLeft.x; }    //Move to the left lane
            //On the Right Lane
            else { m_fScreenTargetX = m_vCenter.x; }
            m_bLaneMoveLeft = true;                     //Set move direction
            return true;    //Can move
        }
        return false; //Cannot move
    }

    /**
     * Check whether player the lane to the right
     */
    private boolean moveLaneRight() {
        if (m_vScreenPos.x < m_vRight.x) {                //Check if on right lane
            //Check if on center lane
            if (m_vScreenPos.x >= m_vCenter.x) { m_fScreenTargetX = m_vRight.x; }   //Move to the right lane
            //On the left lane
            else { m_fScreenTargetX = m_vCenter.x; }    //Move to the center lane
            m_bLaneMoveLeft = false;                    //Set move direction
            return true;
        }
        return false;
    }

    /**
     * Check if player wants to move lanes/ update lane changing
     * @param timeStep
     */
    private void changeLane(float timeStep) {
        if (!m_bChangeLane) {
            if (hud.isLeftButtonDown()) {
                if (moveLaneLeft()) { m_bChangeLane = true; }
            }
            else if (hud.is_bRightButtonDown()) {
                if (moveLaneRight()) { m_bChangeLane = true; }
            }
        }

        //Change lanes by moving screen position/canvas
        if (m_bChangeLane) {
            float fSpeed = 2000.0f;
            float fDistance;
            if (m_bLaneMoveLeft) {
                //Move Left
                m_vScreenPos.x -= fSpeed * timeStep;
                fDistance = m_vScreenPos.x - m_fScreenTargetX;
            } else {
                //Move Right
                m_vScreenPos.x += fSpeed * timeStep;
                fDistance = m_fScreenTargetX - m_vScreenPos.x;
            }
            if (fDistance <= 0) {
                m_bChangeLane = false;
                m_vScreenPos.x = m_fScreenTargetX;
                for (Lane lane: m_aLanes) {
                    //Update which lane player is currently on
                    if (lane.getPosition().x == m_fScreenTargetX) { lane.setOnLane(true); }
                    else { lane.setOnLane(false);}
                }
            }
        }
    }

    public void updateInput (InputHandler updatedInput) {
        m_Input = updatedInput;
        m_Input.relativeTo(m_vScreenPos); //Update tap position relative to canvas
    }

    /**
     * Update all objects
     * @param timeStep
     */
    public void update(float timeStep) {

        changeLane(timeStep);
        for (Lane lane: m_aLanes) { lane.update(timeStep,m_Input,m_bChangeLane);}

        m_Input.relativeTo(m_vScreenPos.multiply(-1.0f));
        hud.updateText(m_iGold, m_iHealth);
        hud.update(m_Input);
    }

    private boolean bShaderSet = false;
    private Paint pShader = new Paint();
    /**
     * Draw all objects
     * @param p
     * @param c
     */
    public void draw(Paint p, Canvas c) {
        pShader.setShader(new BitmapShader(m_Textures.getTexture(2), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));

        c.translate(-m_vScreenPos.x, -m_vScreenPos.y);
        c.drawRect(-m_iScreenWidth, 0, m_iScreenWidth * 2, m_iScreenHeight, pShader);
        //for (Launcher launcher: m_aLaunchers) { launcher.draw(p,c); }

        //for (Lane lane: m_aLanes) { lane.draw(p,c); }
        m_aLanes.get(0).draw(p,c);
        m_aLanes.get(1).draw(p,c);
        m_aLanes.get(2).draw(p, c);
        m_Input.draw(p, c);
        baseWar.draw(p,c);

        c.translate(m_vScreenPos.x, m_vScreenPos.y);
        hud.draw(p, c);

    }
}
