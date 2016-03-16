package com.rzsavilla.onetapgame.model;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public class Destroyable {
    private boolean m_bDestroy = false;           //Destroy Object

    public void destroy() {
        m_bDestroy = true;
    }

    public boolean isDestroyed() {
        return m_bDestroy;
    }
}
