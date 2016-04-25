package com.rzsavilla.onetapgame.Scene;

import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.Log;

import com.rzsavilla.onetapgame.R;
import com.rzsavilla.onetapgame.model.Handler.InputHandler;
import com.rzsavilla.onetapgame.model.Handler.TextureHandler;
import com.rzsavilla.onetapgame.model.Utilites.Calculation;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

import java.util.ArrayList;
import java.util.Iterator;

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
        m_vLeft = new Vector2D(-m_iScreenWidth,0.0f);
        m_vRight = new Vector2D(m_iScreenWidth,0.0f);
        loadTextures();
        loadLanes();
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
    private boolean loadLanes() {
        //Add lanes into array
        m_aLanes.add(new Lane(m_vCenter,m_iScreenWidth,m_iScreenHeight,m_Textures));
        m_aLanes.add(new Lane(m_vCenter,m_iScreenWidth,m_iScreenHeight,m_Textures));
        m_aLanes.add(new Lane(m_vCenter,m_iScreenWidth,m_iScreenHeight,m_Textures));
        return true;
    }

    private void changeLane() {

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
        for (Lane lane: m_aLanes) { lane.update(timeStep,m_Input);}
    }

    private boolean bShaderSet = false;
    private Paint pShader = new Paint();
    /**
     * Draw all objects
     * @param p
     * @param c
     */
    public void draw(Paint p, Canvas c) {
        if (!bShaderSet) {

            bShaderSet = true;
        }
        pShader.setShader(new BitmapShader(m_Textures.getTexture(2), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));

        c.translate(-m_vScreenPos.x, -m_vScreenPos.y);
        c.drawRect(-m_iScreenWidth, 0, m_iScreenWidth * 2, m_iScreenHeight, pShader);
        //for (Launcher launcher: m_aLaunchers) { launcher.draw(p,c); }

        for (Lane lane: m_aLanes) { lane.draw(p,c); }
        m_Input.draw(p,c);
    }
}
