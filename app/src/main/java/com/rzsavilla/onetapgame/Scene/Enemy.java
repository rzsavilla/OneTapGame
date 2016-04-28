package com.rzsavilla.onetapgame.Scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.rzsavilla.onetapgame.Sprite.Enemy.Entity;
import com.rzsavilla.onetapgame.Sprite.Sprite;
import com.rzsavilla.onetapgame.model.Handler.ProjectileHandler;
import com.rzsavilla.onetapgame.model.MyText;
import com.rzsavilla.onetapgame.model.Utilites.Elapsed;

/**
 * Created by rzsavilla on 26/04/2016.
 */
public class Enemy extends Entity{
    private int m_iHealth = 1;
    private int m_iDamage = 1;
    private float m_fHitRate = 1.0f;
    private int m_iValue = 1;
    private boolean m_bTakenDamage = false;
    protected  boolean m_bAttacking = false;
    private MyText m_DamageTakenText = new MyText("",0.0f,0.0f,100.f, Color.WHITE);
    private Elapsed m_TextTimer = new Elapsed();
    private Elapsed m_AttackTimer = new Elapsed();
    /**
     * Default Construct
     */
    public Enemy() {

    }
    public void setHealth(int newHealth) { m_iHealth = newHealth; }

    /**
     * Damage enemy will deal
     * @param newDamage Damage value of this objects attack
     */
    public void setDamage(int newDamage) { m_iDamage = newDamage; }

    /**
     * How often enemy can attack (per second)
     * @param newHitRate
     */
    public void setHitRate(float newHitRate) {m_fHitRate = newHitRate; }

    /**
     * Set points awarded when this enemy is killed
     * @param newValue
     */
    public void setValue(int newValue) { m_iValue = newValue; }

    public int getValue() { return m_iValue; }

    public void takeDamage(int Damage) {
        m_iHealth -= Damage;
        m_DamageTakenText.setTextSize(this.getWidth() / 2.0f);
        m_DamageTakenText.setString("-".concat(Integer.toString(Damage)));
        m_DamageTakenText.setColour(Color.RED);
        m_DamageTakenText.flash(Color.WHITE,0.25f);
        m_bTakenDamage = true;
        m_TextTimer.restart();
    }

    public int getHealth() { return m_iHealth; }
    public int getDamage () { return m_iDamage; }
    public float getHitRate() { return m_fHitRate; }

    public int Attack() {
        if (m_AttackTimer.getElapsed() > m_fHitRate) {
            m_bAttacking = true;
            m_AttackTimer.restart();
            return m_iDamage;
        }
        return 0;
    }

    public boolean canAttack() { return (m_AttackTimer.getElapsed() > m_fHitRate); }

    public void update(float timeStep) {
        //Override entity update
        super.update(timeStep);
        if (m_iHealth <= 0) { destroy(); }
    }

    public void draw(Paint p,Canvas c) {
        //Override entity draw
        super.draw(p,c);
        //Text Pop up to show damage taken
        if (this.m_bTakenDamage) {
            //Text
            m_DamageTakenText.setPosition(this.getPosition().x, this.getPosition().y - this.getHeight() / 4.0f);
            m_DamageTakenText.draw(p, c);

            if (m_TextTimer.getElapsed() >= 0.5f) { this.m_bTakenDamage = false;}
        }
    }


}

