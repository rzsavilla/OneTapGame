package com.rzsavilla.onetapgame.model.Abstract;

/**
 * Created by rzsavilla on 14/03/2016.
 */
public abstract class Destroyable {
    private boolean m_bDestroy = false;           //Destroy Object

    public void destroy() {
        m_bDestroy = true;
    }

    public boolean isDestroyed() {
        return m_bDestroy;
    }
}
