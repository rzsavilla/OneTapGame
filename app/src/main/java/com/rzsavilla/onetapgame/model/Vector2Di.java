package com.rzsavilla.onetapgame.model;

/**
 * Created by rzsavilla on 16/03/2016.
 */
public class Vector2Di {
    public int x = 0;
    public int y = 0;

    //Default Constructor
    public Vector2Di() {
        x = 0;
        y = 0;
    }

    public Vector2Di(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2Di(Vector2Di other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2Di add(Vector2Di other) {
        return new Vector2Di(this.x + other.x , this.y + other.y);
    }
    public Vector2Di subtract(Vector2Di other) {
        return new Vector2Di(this.x - other.x , this.y - other.y);
    }
    public Vector2Di multiply(Vector2Di other) {
        return new Vector2Di(this.x * other.x , this.y * other.y);
    }
    public Vector2Di divide(Vector2Di other) {
        return new Vector2Di(this.x / other.x , this.y / other.y);
    }
    //Scalar
    public Vector2Di multiply(int scalar) {
        return new Vector2Di(this.x * scalar , this.y * scalar);
    }
    public Vector2Di divide(int scalar) {
        if (scalar != 0) {
            return new Vector2Di(this.x / scalar , this.y / scalar);
        } else {
            return new Vector2Di(this.x,this.y);
        }
    }
    public int magnitude() {
        // sqrt(x^2 + y^2)
        return (int)(Math.sqrt((this.x * this.x) + (this.y * this.y)));
    }
    public Vector2Di unitVector() {
        return new Vector2Di(this.x / this.magnitude(), this.y / this.magnitude());
    }
}
