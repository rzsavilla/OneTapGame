package com.rzsavilla.onetapgame.model.Animation;

import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

/**
 * Created by rzsavilla on 16/03/2016.
 */
public class Animation {
    private int m_iFrameCount = 0;
    private Vector2Di m_viFrameSize = new Vector2Di();


    public void setFrameCount(int frames) {
        m_iFrameCount = frames;
    }

    public void setFrameSize(Vector2Di newSize) {
        m_viFrameSize = newSize;
    }

    public void setFrameSize(int width, int height) {
        m_viFrameSize.x = width;
        m_viFrameSize.y = height;
    }

    public int getFrameCount() {
        return m_iFrameCount;
    }

    public Vector2Di getFrameSize() {
        return m_viFrameSize;
    }
}
