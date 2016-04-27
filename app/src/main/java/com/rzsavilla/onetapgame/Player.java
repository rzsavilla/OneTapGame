package com.rzsavilla.onetapgame;

/**
 * Created by rzsavilla on 27/04/2016.
 */
public class Player implements Comparable<Player>{
    private String sName = "";
    private float fScore = 0;

    public Player(String Name, float Score) {
        sName = Name;
        fScore = Score;
    }
    public String getName() { return sName; }
    public float getScore() { return fScore; }

    public int compareTo(Player other) {
        if (other.getScore() < this.fScore) { return -1; }
        if (other.getScore() > this.fScore) { return  1; }
        return  0;
    }
}
