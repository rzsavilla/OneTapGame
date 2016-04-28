package com.rzsavilla.onetapgame.model.Handler;

import android.content.Context;
import android.media.MediaPlayer;

import com.rzsavilla.onetapgame.R;
import com.rzsavilla.onetapgame.model.Projectiles.Projectile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by rzsavilla on 27/04/2016.
 */
public class SoundHandler {
    private Context m_Context;
    private float m_fEffectsVolume = 50.0f;         //0-50
    private float m_fMusicVolume = 25.0f;
    private boolean m_bStop = true;

    private ArrayList<MediaPlayer> m_aSound = new ArrayList<>();

    /**
     * Default constructor
     */
    public SoundHandler(Context context) {
        m_Context = context;
    }

    /**
     * Add sound to array of sounds
     * @param Sound Sound to be added
     */
    public void add(MediaPlayer Sound) { m_aSound.add(Sound); }

    /**
     * Set Maximum volume of sound played by this handler
     * @param Volume New maximumg volume
     */
    public void setEffectsVolume(float Volume) { m_fEffectsVolume = Volume; }

    /**
     * Play sound based on array index
     * @param index Sound to play
     */
    public void playSound(int index) {
        m_bStop = false;
        if (m_aSound.size() < 20) {
            switch (index) {
                case 0:
                    //Shoot sound
                    m_aSound.add(new MediaPlayer().create(m_Context, R.raw.shoot));
                    m_aSound.get(m_aSound.size() - 1).setVolume(m_fEffectsVolume, m_fEffectsVolume);
                    m_aSound.get(m_aSound.size() - 1).start();
                    break;
                case 1:
                    //Hit Sound
                    m_aSound.add(new MediaPlayer().create(m_Context, R.raw.hit));
                    m_aSound.get(m_aSound.size() - 1).setVolume(m_fEffectsVolume, m_fEffectsVolume);
                    m_aSound.get(m_aSound.size() - 1).start();
                    break;
                case 2:
                    m_aSound.add(new MediaPlayer().create(m_Context, R.raw.music));
                    m_aSound.get(m_aSound.size() - 1).setVolume(m_fMusicVolume, m_fMusicVolume);
                    m_aSound.get(m_aSound.size() - 1).setLooping(true);
                    m_aSound.get(m_aSound.size() - 1).start();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Release all sounds
     */
    public void release() {
        for (MediaPlayer sound: m_aSound) {
            sound.stop();
            sound.release();
            m_bStop = true;
        }
    }

    /**
     * Remove sounds that have finished and are not looping
     */
    public void update() {
        if (!m_bStop) {
            ListIterator<MediaPlayer> itr = m_aSound.listIterator();
            while (itr.hasNext()) {                      //Iterate through bullets
                MediaPlayer e = itr.next();
                if (!e.isPlaying()&& !e.isLooping()) {
                    e.release();
                    itr.remove();
                }
            }
        }
    }
}
