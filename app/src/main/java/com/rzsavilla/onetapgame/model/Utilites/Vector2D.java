package com.rzsavilla.onetapgame.model.Utilites;

public class Vector2D {
    public float x = 0.0f;
    public float y = 0.0f;

    public Vector2D() {                //Default Contructor
        x = 0.0f;
        y = 0.0f;
    }

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x , this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x , this.y - other.y);
    }

    public Vector2D multiply(Vector2D other) {
        return new Vector2D(this.x * other.x , this.y * other.y);
    }

    public Vector2D divide(Vector2D other) {
        return new Vector2D(this.x / other.x , this.y / other.y);
    }

    //Scalar
    public Vector2D add(float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    public Vector2D multiply(float scalar) {
        return new Vector2D(this.x * scalar , this.y * scalar);
    }

    public Vector2D divide(float scalar) {
        if (scalar != 0) {
            return new Vector2D(this.x / scalar , this.y / scalar);
        } else {
            return new Vector2D(this.x,this.y);
        }
    }

    public float magnitude() {
        // sqrt(x^2 + y^2)
        return (float)(Math.sqrt((this.x * this.x) + (this.y * this.y)));
    }

    public Vector2D unitVector() {
        return new Vector2D(this.x / this.magnitude(), this.y / this.magnitude());
    }

    public float dot(Vector2D other) {
        return (this.x * other.x) + (this.y * other.y);
    }
}