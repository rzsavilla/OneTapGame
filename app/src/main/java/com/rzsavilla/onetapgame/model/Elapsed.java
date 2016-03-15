package com.rzsavilla.onetapgame.model;

/**
 * Created by rzsavilla on 15/03/2016.
 */
public class Elapsed {
    private long m_StartTime = 0l;      //Start time in milliseconds
    private long m_StopTime = 0l;       //Time stopped in milliseconds;
    private long m_lElapsed = 0l;       //Elapsed time in milliseconds

    //Deafault Constructor
    public Elapsed() {

    }

    public void restart() {
        m_StartTime = System.currentTimeMillis();
    }

    public long stop() {
        m_StopTime = System.currentTimeMillis();
        return m_StopTime;
    }



    public float getElapsed() {
        return (float) (System.currentTimeMillis() - m_StartTime) / 1000;
    }
}
