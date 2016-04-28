package com.rzsavilla.onetapgame.model.Utilites;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Abstract.Transformable;

/**
 * Created by rzsavilla on 25/04/2016.
 */
public class MyText extends Transformable {
    private String m_sString = "";
    private int m_iColour = Color.WHITE;
    private float m_fSize = 10.0f;
    private boolean m_bFlash;
    private int m_iFlashColour = Color.RED;
    private float m_fFlashDuratioin;
    private Elapsed m_Timer = new Elapsed();

    /**
     * Default Constructor
     */
    public MyText() {}

    public MyText(String string, float posX, float posY, float Size, int Colour) {
        setString(string);
        setPosition(posX, posY);
        setTextSize(Size);
    }

    public void setColour(int Colour) { m_iColour = Colour; }
    public void setString(String newString) { m_sString = newString; }
    public void setTextSize(float Size) { m_fSize = Size; }

    public void flash(int Colour,float Duration) {
        m_bFlash = true;
        m_fFlashDuratioin = Duration;
        m_iFlashColour = Colour;
        m_Timer.restart();
    }

    public void draw(Paint p ,Canvas c) {
        if (m_bFlash && m_Timer.getElapsed() < m_fFlashDuratioin) {
            p.setColor(m_iFlashColour);
        }
        else {
            p.setColor(m_iColour);
            m_bFlash = false;
        }
        p.setTextSize(m_fSize);
        c.drawText(m_sString,getPosition().x,getPosition().y,p);
    }
}
