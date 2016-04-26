package com.rzsavilla.onetapgame.Scene;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.Sprite.Enemy.Entity;
import com.rzsavilla.onetapgame.Sprite.Sprite;
import com.rzsavilla.onetapgame.model.Handler.ProjectileHandler;
import com.rzsavilla.onetapgame.model.Utilites.Elapsed;

/**
 * Created by rzsavilla on 25/04/2016.
 */
public class Warrior extends Entity implements Cloneable{
    public ProjectileHandler m_Weapon = new ProjectileHandler();
    //private Vector2D m_WeaponSpawn = new Vector2D();
    public Sprite m_WeaponSprite = new Sprite();

    Elapsed m_Timer = new Elapsed();
    private float m_fDamage = 1.0f;
    private float m_fHitRate = 0.5f;
    private boolean m_bAttack = false;

    /**
     * Default Constructor
     */
    public Warrior() {

    }


    public Warrior(Warrior copy) {
        this.setPosition(copy.getPosition());
        this.setOrigin(copy.getOrigin());
        this.setSize(copy.getSize());
        this.setForce(copy.getForce());
        this.setVelocity(copy.getVelocity());
        this.setDamage(copy.getDamage());
        this.setHitRate(copy.getHitRate());
        this.setHealth(copy.getHealth());

        this.m_WeaponSprite = copy.m_WeaponSprite;
        this.m_Weapon =  copy.m_Weapon;

        if (copy.m_bHasTexture) {
            this.m_bHasTexture = copy.m_bHasTexture;
            this.setAnimationSpeed(copy.getAnimationSpeed());
            this.dst = copy.dst;
            this.src = copy.src;
            this.setAnimatedSprite(copy);
            this.setSpriteSheet((Bitmap)copy.m_Texture,this.getFrameSize(),this.getFrameCount());
            this.m_Texture = (Bitmap)copy.m_Texture;
        }
        //this.setSpriteSheet(copy.getTexture(), copy.getFrameSize(), copy.getFrameCount());
    }

    public void copy(Warrior copy) {
        Warrior clone = new Warrior();
        clone = copy;
        this.setPosition(clone.getPosition());
        this.setOrigin(clone.getOrigin());
        this.setSize(clone.getSize());
        this.setForce(clone.getForce());
        this.setVelocity(clone.getVelocity());
        this.setDamage(clone.getDamage());
        this.setHitRate(clone.getHitRate());
        this.setHealth(clone.getHealth());

        this.m_WeaponSprite = clone.m_WeaponSprite;
        this.m_Weapon =  clone.m_Weapon;

        if (clone.m_bHasTexture) {
            this.m_bHasTexture = clone.m_bHasTexture;
            this.setAnimationSpeed(copy.getAnimationSpeed());
            this.dst = clone.dst;
            this.src = clone.src;
            this.setAnimatedSprite(clone);
            this.setSpriteSheet((Bitmap)clone.m_Texture,this.getFrameSize(),this.getFrameCount());
            this.m_Texture = (Bitmap)clone.m_Texture;
        }
    }


    public void setDamage(float newDamage) { m_fDamage = newDamage; }
    public void setHitRate(float newHitRate) {m_fHitRate = newHitRate; }

    public float getDamage() { return m_fDamage; }
    public float getHitRate() { return m_fHitRate; }

    public void update(float timeStep) {
        super.update(timeStep);
        if (m_bAttack && m_Timer.getElapsed() > m_fHitRate) {

            m_bAttack = false;
            m_Timer.restart();
        }

        if (bPositionChanged) {
            //m_Weapon.setPosition(this.getPosition());
        }
    }

    public  void draw(Paint p, Canvas c) {
        super.draw(p, c);
        m_Weapon.drawProj(p, c);
    }

    public Warrior clone() throws  CloneNotSupportedException {
        Warrior newWarrior = (Warrior) super.clone();

        return  newWarrior;
    }
}
