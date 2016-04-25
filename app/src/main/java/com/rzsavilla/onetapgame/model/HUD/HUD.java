package com.rzsavilla.onetapgame.model.HUD;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.renderscript.ScriptGroup;

import com.rzsavilla.onetapgame.model.Handler.InputHandler;
import com.rzsavilla.onetapgame.model.Handler.TextureHandler;
import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
import com.rzsavilla.onetapgame.model.Shapes.Collision.Circle;
import com.rzsavilla.onetapgame.model.Shapes.RectangleShape;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

/**
 * Head up display draws in game user interface and shows information
 */
public class HUD {
    //Positioning
    private int m_iScreenWidth = 100;
    private int m_iScreenHeight = 200;
    private Vector2Di m_viScreenSize = new Vector2Di();         //Used for positioning HUD elements
    private Vector2D m_vPosition = new Vector2D();              //Position of the HUD elements

    CircleShape m_LeftButton = new CircleShape();
    CircleShape m_RightButton = new CircleShape();

    private boolean m_bLeftButtonDown = false;
    private boolean m_bRightButtonDown = false;

    public HUD() {

    }

    public void initialize(int width, int height,TextureHandler textures) {

    }

    public boolean isLeftButtonDown() { return m_bLeftButtonDown; }
    public boolean isM_bRightButtonDown() { return  m_bRightButtonDown; }

    public void update(InputHandler input) {
        if (input.isDown()) {
            if (input.tap(m_LeftButton)) {

            } else if (input.tap(m_RightButton)){

            }
        }
    }

    public void draw(Paint p , Canvas c) {

    }
}
