package com.rzsavilla.onetapgame.Scene;

import android.app.WallpaperInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.rzsavilla.onetapgame.Sprite.Enemy.Entity;
import com.rzsavilla.onetapgame.model.Handler.InputHandler;
import com.rzsavilla.onetapgame.model.Handler.TextureHandler;
import com.rzsavilla.onetapgame.model.Projectiles.Projectile;
import com.rzsavilla.onetapgame.model.Shapes.Collision.AABB;
import com.rzsavilla.onetapgame.model.Shapes.Collision.Circle;
import com.rzsavilla.onetapgame.model.Shapes.RectangleShape;
import com.rzsavilla.onetapgame.model.Utilites.Elapsed;
import com.rzsavilla.onetapgame.model.Utilites.Transformable;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

/**
 * Lanes will spawn enemies, three lanes in total
 * Left, Center and Right lanes.
 * Controls stores and updates enemies
 */
public class Lane extends Transformable{
    private boolean m_bOnLane = false;                  //Player is viewing this lane
    private ArrayList<Enemy> m_aEnemies = new ArrayList<>();       //Array of enemies
    private ArrayList<Circle> m_Explosion = new ArrayList<>();
    private int m_iWallDamage = 0;
    private int m_iNewScore = 0;
    private Launcher m_Launcher;
    private AABB m_Wall;
    private AABB m_LeftWall;
    private AABB m_RightWall;

    private Elapsed m_Timer = new Elapsed();
    private EnemyWave m_EnemyWave = new EnemyWave();
    private boolean m_bFinished = false;

    public TextureHandler m_Textures;

    /**
     * Constructor
     * @param position
     * @param width
     * @param height
     * @param textures
     */
    public Lane(Vector2D position, int width, int height,TextureHandler textures) {
        setPosition(position);
        setSize(width, height);
        initialize(textures);
    }

    /**
     *
     * @param textures
     * @return
     */
    public boolean initialize(TextureHandler textures) {
        m_Textures = textures;
        float fCentreOffset = getSize().x / 2.0f;
        float fHeight;
        m_Launcher = new Launcher();
        m_Launcher.m_Sprite.setTexture(textures.getTexture(0));
        fHeight = m_Launcher.m_Sprite.getHeight();
        m_Launcher.setOrigin(m_Launcher.m_Sprite.getWidth() / 2.0f, m_Launcher.m_Sprite.getHeight() / 2.0f);
        m_Launcher.setPosition(this.getPosition().add(new Vector2D(fCentreOffset, getSize().y - fHeight)));

        m_Wall = new AABB();
        m_Wall.setSize(this.getWidth(), m_Launcher.m_Sprite.getHeight() * 2);
        m_Wall.setPosition(this.getPosition().x, this.getHeight() - m_Wall.getHeight());
        m_Wall.updateGlobalBounds();
        m_Wall.setColour(Color.GRAY);

        float fWidth = 1.0f;
        m_LeftWall = new AABB();
        m_LeftWall.setSize(fWidth, this.getHeight());
        m_LeftWall.setPosition(this.getPosition());
        m_LeftWall.updateGlobalBounds();
        m_LeftWall.setColour(Color.RED);

        m_RightWall = new AABB(this.getPosition().x + this.getWidth() - fWidth,this.getPosition().y,fWidth,this.getHeight(),Color.BLUE);

        return true;
    }

    /**
     * Set whether player is viewing this lane
     * @param onLane
     * @return
     */
    public void setOnLane(boolean onLane) { m_bOnLane = onLane; }

    private void setEnemyWave(EnemyWave wave) { m_EnemyWave = wave; }

    Random random = new Random();
    private void spawn(int i) {

        switch (i) {
            case 0:
                //No enemy spawned
                break;
            case 1:
                //Spawn a Warrior
                Warrior warrior = new Warrior();
                int iMin = (int) warrior.getWidth();
                int iMax = (int) this.getSize().x - (int) warrior.getWidth() * 2;
                //iMin = 400;
                //iMax = 1000;

                random.nextInt();
                boolean bOverlap = false;
                float randX;
                randX = (float)random.nextInt(iMax) + iMin;
                warrior.setPosition(randX + this.getPosition().x, this.getPosition().y + 900);
                warrior.setHealth(2);
                warrior.setHitRate(0.5f);
                warrior.setVelocity(0.0f, 1.0f);
                warrior.setForce(1000.0f);
                warrior.setSpriteSheet(m_Textures.getTexture(1), new Vector2Di(250, 250), new Vector2Di(4, 1));
                warrior.setWeapon(m_Textures.getTexture(4));
                warrior.bb.setRadius(warrior.getWidth() / 2.0f);
                m_aEnemies.add(warrior);

                break;
            case 2:
                break;
            case 3:
        }
    }

    private void spawnUpdate() {
        if (true ||!m_EnemyWave.getWave().isEmpty()) {
            if (m_Timer.getElapsed() >= 5.0f && m_aEnemies.size() < 1) {
                    //Log.d("Spawning", "Spawned");
                    spawn(1);
                    //spawn(m_EnemyWave.getWave().get(i));                    //Spawn enemy
                    //m_EnemyWave.getWave().remove(i);                            //Remove spawned enemy id from array
                    m_Timer.restart();
            }
        }
        else if (m_aEnemies.isEmpty()) {
            m_bFinished = true;
        }
    }

    public boolean isWaveFinished() { return m_bFinished; }

    public int getWallDamage() {
        int i = m_iWallDamage;
        m_iWallDamage = 0;
        return  i;
    }
    public int getScore() {
        int i = m_iNewScore;
        m_iNewScore = 0;
        return i;
    }

    /**
     * Update Lane objects
     * @param timeStep
     * @param input
     */
    public void update(float timeStep, InputHandler input, boolean changingLanes) {
        //Update Launcher
        if (m_bOnLane && !changingLanes) {
            if (input.isDown() && input.getTapPos().y < 2000.0f){
                m_Launcher.markTarget(input.getTapPos());
            }
        }
        m_Launcher.update(timeStep);

        //Spawn enemies
        spawnUpdate();

        //Enemy hit by projectile
        for (Enemy enemy: m_aEnemies) {
            for (Projectile proj: m_Launcher.m_Bullets.m_aProjectiles) {
                if (proj.impulse(enemy)) {
                    enemy.takeDamage(1);
                    proj.destroy();
                }
            }
        }

        //Enemy-Enemy Collision
        for (int i = 0; i < m_aEnemies.size(); i++) {
            for (int j = i+1; j < m_aEnemies.size(); j++) {
                if (m_aEnemies.get(i).impulse(m_aEnemies.get(j))) {}
            }
        }

        //Update All Enemies
        for (Enemy enemy: m_aEnemies) {
            /*
            float fLimit = 1.0f;
            Vector2D velLimit = new Vector2D(0.0f,0.0f);
            if (enemy.getVelocity().y > fLimit) {
                enemy.setVelocity(enemy.getVelocity().x,fLimit);
            } else if (enemy.getVelocity().y < -fLimit) {
                enemy.setVelocity(enemy.getVelocity().x,-fLimit);
            }
            if (enemy.getVelocity().x > fLimit) {
                enemy.setVelocity(fLimit, enemy.getVelocity().y);
            } else if (enemy.getVelocity().x < -fLimit) {
                enemy.setVelocity(-fLimit, enemy.getVelocity().x);
            }
            */
            //Wall Collision
            if (enemy.impulseStatic(m_Wall)) {
                m_iWallDamage = enemy.Attack();
                //Log.d("Attack","Wall");
            }
            if (enemy.impulseStatic(m_LeftWall)) { enemy.setVelX(1.0f); }
            else if (enemy.impulseStatic(m_RightWall)) { enemy.setVelX(-1.0f); }

            enemy.update(timeStep);
            if (enemy.getVelocity().y < 1.0f) {
                enemy.setVelocity(enemy.getVelocity().x, enemy.getVelocity().y + 0.1f);
            }
        }
        //Remove destroyed enemies
        ListIterator<Enemy> itr = m_aEnemies.listIterator();
        while(itr.hasNext()) {
            Enemy e = itr.next();
            if (e.isDestroyed()) {
                m_iNewScore += e.getValue();
                itr.remove();
            }
        }
    }

    /**
     * Draw all objects on the lane
     * @param p
     * @param c
     */
    public void draw(Paint p, Canvas c) {
        this.m_Wall.draw(p,c);
        this.m_LeftWall.draw(p,c);
        this.m_RightWall.draw(p,c);

        for (int i = m_aEnemies.size()-1; i >=  0; i--) {
            //Draw array reverse // Shows enemies at the front first
            m_aEnemies.get(i).draw(p,c);
        }
        //for (Entity enemy: m_aEnemies) { enemy.draw(p,c); }
        this.m_Launcher.draw(p,c);
    }
}