package com.rzsavilla.onetapgame.model;

import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

/**
 * Singleton Class
 */
public class Calculation {

    private static Calculation m_Calculation = new Calculation();

    private Calculation() {};       //Constructor

    public static Calculation getInstance() {
        return m_Calculation;
    }

    public float getDistance(Vector2D v1, Vector2D v2) {
        return (float)Math.sqrt(Math.pow(v2.x - v1.x,2) - Math.pow(v2.y - v1.y,2));
    }
}
