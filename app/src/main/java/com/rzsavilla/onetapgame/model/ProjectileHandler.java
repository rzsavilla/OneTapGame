package com.rzsavilla.onetapgame.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Bullet Spawner
 */
public class ProjectileHandler extends RectangleShape{
    private ArrayList<Projectile> m_aProjectiles = new ArrayList();           //Stores all bullets

    private Projectile m_Projectile = new Projectile();

    //Default Constructor
    public ProjectileHandler() {
        setPosition(0.0f, 0.f);
        setColour(Color.GREEN);
        setSize(100.0f, 100.0f);
    }

    public void shoot(Vector2D target) {
        m_Projectile.setTarget(target);
        m_aProjectiles.add(m_Projectile);
    }

    public void shoot(float x, float y) {
        m_Projectile = new Projectile(0,0,0,0);
        m_Projectile.setPosition(getPosition());
        m_Projectile.setTarget(x, y);
        m_aProjectiles.add(m_Projectile);

    }

    public void drawProj(Paint p, Canvas c) {
        for (int i = 0; i < m_aProjectiles.size(); i++) {
            m_aProjectiles.get(i).draw(p, c);
        }
    }

    public void update(float timeStep) {
        for (int i = 0; i < m_aProjectiles.size(); i++) {
            m_aProjectiles.get(i).update(timeStep);
            System.out.println(m_aProjectiles.get(i).getPosition().x);
        }
        System.out.println();
    }
}
