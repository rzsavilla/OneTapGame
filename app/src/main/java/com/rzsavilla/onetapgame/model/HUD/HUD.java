package com.rzsavilla.onetapgame.model.HUD;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Handler.InputHandler;
import com.rzsavilla.onetapgame.model.Handler.TextureHandler;
import com.rzsavilla.onetapgame.model.Utilites.MyText;
import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
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

    private int m_iScore = 0;
    private int m_iWave = 1;
    private int m_iHealth = 50;
    private int m_iGold = 50;

    private RectangleShape m_HealthBar = new RectangleShape();

    private CircleShape m_LeftButton = new CircleShape();
    private CircleShape m_RightButton = new CircleShape();

    private RectangleShape m_TopBar = new RectangleShape();

    private MyText m_GoldText = new MyText();
    private MyText m_HealthText = new MyText();

    private boolean m_bLeftButtonDown = false;
    private boolean m_bRightButtonDown = false;

    public HUD() {

    }

    public void initialize(int width, int height,TextureHandler textures) {
        float fOffset = height * 0.05f;
        float fOffsetY = height * 0.06f;
        m_LeftButton.setRadius(width * 0.1f);
        m_LeftButton.setColour(Color.RED);
        m_LeftButton.setPosition(m_LeftButton.getRadius() * 2, (height - m_LeftButton.getRadius() - fOffset));

        m_RightButton.setRadius(width * 0.1f);
        m_RightButton.setColour(Color.RED);
        m_RightButton.setPosition(width - (m_RightButton.getRadius() * 2), (height - m_RightButton.getRadius() - fOffset));

        //Background TOP BAR
        m_TopBar.setPosition(0.0f, 0.0f);
        m_TopBar.setSize(width, fOffset * 1.5f);
        m_TopBar.setColour(Color.BLACK);

        m_GoldText.setString("Score:");
        m_GoldText.setColour(Color.YELLOW);
        m_GoldText.setTextSize(fOffset);
        m_GoldText.setPosition(m_TopBar.getPosition().add(new Vector2D(10.0f, m_TopBar.getPosition().y + fOffsetY)));

        m_HealthText.setString("Health:  ");
        m_HealthText.setTextSize(fOffset);
        m_HealthText.setColour(Color.RED);
        m_HealthText.setPosition(width - (width/2),m_TopBar.getPosition().y + fOffsetY);
    }

    public boolean isLeftButtonDown() { return m_bLeftButtonDown; }
    public boolean is_bRightButtonDown() { return  m_bRightButtonDown; }

    public void updateText(int Time, int Health) {
        m_GoldText.setString("Score:".concat(Integer.toString(Time)));
        m_HealthText.setString("Health:".concat(Integer.toString(Health)));
    }

    public void update(InputHandler input) {
        if (input.isDown()) {
            if (input.tap(m_LeftButton)) { m_bLeftButtonDown = true; }
            else if (input.tap(m_RightButton)){ m_bRightButtonDown = true; }
        }
        else { m_bLeftButtonDown = false; m_bRightButtonDown = false; }
    }

    public void draw(Paint p , Canvas c) {
        m_TopBar.draw(p, c);
        m_GoldText.draw(p,c);
        m_HealthText.draw(p,c);

        m_LeftButton.draw(p, c);
        m_RightButton.draw(p, c);
    }
}
