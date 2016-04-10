package com.rzsavilla.onetapgame.model.HUD;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.model.Utilites.Vector2D;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

/**
 * Head up display draws in game user interface and shows information
 */
public class HUD {
    //Positioning
    private Vector2Di m_viScreenSize = new Vector2Di();         //Used for positioning HUD elements
    private Vector2D m_vPosition = new Vector2D();              //Position of the HUD elements

    public HUD() {

    }

    public void draw(Paint p , Canvas c) {

    }
}
