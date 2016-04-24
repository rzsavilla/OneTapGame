package com.rzsavilla.onetapgame.model.Interface;

import com.rzsavilla.onetapgame.model.Shapes.CircleShape;
import com.rzsavilla.onetapgame.model.Shapes.Collision.AABB;

/**
 * Created by rzsavilla on 23/03/2016.
 */
public interface Collidable{
    //Collision checks
    /**
     * Check for collision with CircleShape
     * @param other Axis Aligned Bounding Box to check collision with
     * @return returns true if a collision has occured
     */
    boolean collision(AABB other);

    /**
     * Check for collision with CircleShape
     * @param other circle to check collision with
     * @return returns true if a collision has occured
     */
    boolean collision(CircleShape other);

    /**
     * Check for collision AABB
     * @param other Axis Aligned Bounding Box to check collision with
     * @return intersection/overlap distance
     */
    float intersect(AABB other);

    /**
     * Check for collision with CircleShape
     * @param other CircleShape to check collision with.
     * @returns intersection/overlap distance
     */
    float intersect(CircleShape other);
}
