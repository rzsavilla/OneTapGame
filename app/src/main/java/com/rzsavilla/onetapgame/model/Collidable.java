package com.rzsavilla.onetapgame.model;

/**
 * Created by rzsavilla on 23/03/2016.
 */
public interface Collidable {
    boolean collision(AABB other);
    boolean collision(CircleShape other);
}
