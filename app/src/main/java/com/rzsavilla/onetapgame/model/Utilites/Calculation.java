package com.rzsavilla.onetapgame.model.Utilites;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

/**
 * Singleton Class
 * Contains calculation functions
 * @author rzsavilla
 */
public class Calculation {

    /**
     * Instance of the object
     */
    private static Calculation m_Calculation = new Calculation();

    /**
     * Private Default Constructor
     */
    private Calculation() {};       //Constructor

    /**
     * Returns the instanciated Calculation Object
     * @return
     */
    public static Calculation getInstance() {
        return m_Calculation;
    }

    /**
     * Returns the distance between two vectors
     * @param v1 First vector
     * @param v2 Second vector
     * @return The Distance between the two vectors
     */
    public static float getDistance(Vector2D v1, Vector2D v2) {
        return (float)Math.sqrt(Math.pow(v2.x - v1.x,2) - Math.pow(v2.y - v1.y,2));
    }
}
