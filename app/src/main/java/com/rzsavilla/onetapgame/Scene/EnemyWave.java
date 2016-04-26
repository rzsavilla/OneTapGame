package com.rzsavilla.onetapgame.Scene;

import com.rzsavilla.onetapgame.model.Utilites.Vector2D;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by rzsavilla on 25/04/2016.
 */
public class EnemyWave {
    private float m_fSpawnRate = 1.0f; //Spawn every n seconds
    private ArrayList<Integer> m_aWave = new ArrayList<>();

    /**
     * Default Constructor
     */
    public EnemyWave() {

    }

    /**
     * Set spawn frequency in seconds
     * @param seconds
     */
    public void setSpawnRate(float seconds) { m_fSpawnRate = seconds; }


    public float getSpawnRate() { return m_fSpawnRate; }

    /**
     * Add enemy into array, which will notify spawner what enemy to spawn based on the value of the element
     * @param args
     */
    public void add(int[] args) {
        for (int enemy: args) { m_aWave.add(enemy); }
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getWave() { return m_aWave; }
}
