package com.rzsavilla.onetapgame.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Utilites.Transformable;

/**
 * Created by rzsavilla on 25/04/2016.
 */
public class MyText extends Transformable {
    String m_sString = "";
    int m_iColour = Color.WHITE;
    float m_fSize = 10.0f;

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

    public void draw(Paint p ,Canvas c) {
        p.setColor(m_iColour);
        p.setTextSize(m_fSize);
        c.drawText(m_sString,getPosition().x,getPosition().y,p);
    }
}
