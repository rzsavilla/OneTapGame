package com.rzsavilla.onetapgame.model.Collision;

import com.rzsavilla.onetapgame.model.Shapes.CircleShape;

/**
 * Created by rzsavilla on 23/03/2016.
 */
public interface Collidable{
    //Collision checks
    boolean collision(AABB other);
    boolean collision(CircleShape other);
}
