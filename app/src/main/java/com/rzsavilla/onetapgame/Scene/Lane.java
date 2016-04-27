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

    private Elapsed m_Timer = new Elapsed();                //Spawn timer
    private Elapsed m_DifficultyTimer = new Elapsed();
    private EnemyWave m_EnemyWave = new EnemyWave();
    private boolean m_bFinished = false;

    public TextureHandler m_Textures;

    private int m_iMaxSpawn = 2;
    private float m_fSpawnDelay = 5.0f;

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

        this.m_Wall = new AABB();
        this.m_Wall.setSize(this.getWidth(), this.getSize().y * 0.2f);
        this.m_Wall.setPosition(this.getPosition().x, this.getHeight() - m_Wall.getHeight());
        this.m_Wall.updateGlobalBounds();
        this.m_Wall.setColour(Color.GRAY);

        float fWidth = 1.0f;
        this.m_LeftWall = new AABB(this.getPosition().x,this.getPosition().y,fWidth,this.getHeight(),Color.RED);
        this.m_LeftWall.updateGlobalBounds();
        this.m_RightWall = new AABB(this.getPosition().x + this.getWidth() - fWidth,this.getPosition().y,fWidth,this.getHeight(),Color.BLUE);
        this.m_RightWall.updateGlobalBounds();

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
    private boolean spawn(int i) {
        boolean bSuccess = false; //Spawn Successful
        switch (i) {
            case 0:
                //No enemy spawned
                break;
            case 1:
                //Spawn a Warrior
                Warrior warrior = new Warrior();
                int iMin = (int) warrior.getWidth() + (int)(this.getWidth() * 0.1f);
                int iMax = (int) this.getSize().x - (int) warrior.getWidth() * 2;
                float fRange = (iMax - iMin) + 1;
                //iMin = 400;
                //iMax = 1000;

                random.nextInt();
                boolean bOverlap = false;
                float fX = 0.0f;
                float fY = 0.0f;
                int iCounter = 0; //Retries before spawn is skipped
                do {
                    // Generate random position
                    fX = ((float) (Math.random() * fRange) + iMin) + this.getPosition().x;
                    //fX = ((float)random.nextInt(iMax) + iMin) + this.getPosition().x;
                    Log.d("Spawn pos:",Float.toString(fX - this.getPosition().x));
                    fY = warrior.getHeight();
                    warrior.setPosition(fX,fY);
                    if (warrior.bb.collision(this.m_LeftWall)) {
                        warrior.setPosition(fX + warrior.getWidth(),fY);
                    }
                    else if (warrior.bb.collision(this.m_RightWall)) {
                        warrior.setPosition(fX - warrior.getWidth(),fY);
                    }
                    for (Enemy spawned: m_aEnemies) {
                        if (warrior.bb.collision(spawned.bb)) {
                            bOverlap = true;
                            break;
                        }
                    }
                    iCounter++;
                }
                while(bOverlap && iCounter < 5);
                if (iCounter >= 5) {
                    bSuccess = false;
                    break;
                }

                Log.d("Spawn pos:", Float.toString(fX + this.getPosition().x));
                warrior.setHealth(2);
                warrior.setHitRate(1.0f);

                warrior.setForce(500.0f);
                warrior.setSpriteSheet(m_Textures.getTexture(1), new Vector2Di(250, 250), new Vector2Di(4, 1));
                warrior.setWeapon(m_Textures.getTexture(4));
                warrior.bb.setRadius(warrior.getWidth() / 2.0f);
                warrior.setVelocity(0.0f, 1.0f);
                m_aEnemies.add(warrior);
                bSuccess = true;
                break;
            case 2:
                break;
            case 3:
        }
        return bSuccess;
    }

    private void spawnUpdate() {
        if (true || !m_EnemyWave.getWave().isEmpty()) {
            if (m_Timer.getElapsed() >= m_fSpawnDelay && m_aEnemies.size() < m_iMaxSpawn) {
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
        m_iWallDamage = 0;          //Reset
        return  i;
    }
    public int getScore() {
        int i = m_iNewScore;
        m_iNewScore = 0;            //Reset
        return i;
    }

    public void increaseDifficulty() {
        //Increase difficulty over time
        if (m_DifficultyTimer.getElapsed() > 30.0f) {
            if (m_iMaxSpawn < 20) {m_iMaxSpawn += 1;}
            if (m_fSpawnDelay > 2.0f) {m_fSpawnDelay -= 0.05f;}
            m_DifficultyTimer.restart();
        }
    }

    /**
     * Update Lane objects
     * @param timeStep
     * @param input
     */
    public void update(float timeStep, InputHandler input, boolean changingLanes) {
        increaseDifficulty();

        //Update Launcher
        if (m_bOnLane && !changingLanes) {
            if (input.isDown() && input.getTapPos().y < this.m_Wall.getPosition().y){
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
                m_iWallDamage += enemy.Attack();
                //Log.d("Attack","Wall");
            }
            if (enemy.impulseStatic(m_LeftWall)) {  enemy.setVelocity(1.0f,enemy.getVelocity().y);}
            else if (enemy.impulseStatic(m_RightWall)) { enemy.setVelocity(-1.0f,enemy.getVelocity().y); }

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