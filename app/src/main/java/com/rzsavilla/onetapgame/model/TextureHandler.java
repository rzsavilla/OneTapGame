package com.rzsavilla.onetapgame.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;

import com.rzsavilla.onetapgame.R;

import java.util.ArrayList;

/**
 * Will Load and scale Bitmaps
 */
public class TextureHandler {
    private Vector2Di m_vScreenSize = new Vector2Di();
    private Point m_pScreenSize = new Point();
    private ArrayList<Bitmap> m_aTextures = new ArrayList<Bitmap>();
    private boolean m_bHasContext;
    private boolean m_bScreenSet;
    private Context m_context;
    private float m_fScreenDensity;
    private Vector2D m_vScale = new Vector2D();

    public void TextureHandler(Context contextIn) {
        m_context = contextIn;
    }

    public void TextureHandler(Context contextIn, Vector2Di screenSize) {
        m_context = contextIn;
        m_vScreenSize = screenSize;
    }

    public void TextureHandler(Context contextIn, Point screenSize) {
        m_context = contextIn;
        m_vScreenSize.x = screenSize.x;
        m_vScreenSize.y = screenSize.y;
    }

    public void setScreenSize(Vector2Di newScreenSize) {
        m_vScreenSize = newScreenSize;
    }

    public void setScale(Vector2D newScale) {
        m_bScreenSet = true;
        m_vScale = newScale;
    }

    public void setScreenSize(Point newScreenSize) {
        m_vScreenSize.x = newScreenSize.x;
        m_vScreenSize.y = newScreenSize.y;
        this.m_pScreenSize = newScreenSize;
        m_bScreenSet = true;
    }

    public void setContext(Context contextIn) {
        m_context = contextIn;
        m_fScreenDensity = m_context.getResources().getDisplayMetrics().density;
        m_bHasContext = true;
    }
    public boolean loadBitmap(int id) {
        if (m_bHasContext && m_bScreenSet) {
            Bitmap texture;
            texture = BitmapFactory.decodeResource(m_context.getResources(), id);
            float fScaleX = texture.getWidth() * (1);
            float fScaleY = texture.getHeight() * (1);
            texture = Bitmap.createScaledBitmap(texture, (int)fScaleX, (int)fScaleY, false);
            m_aTextures.add(texture);
            return true;
        } else {
            return false;
        }
    }

    public Bitmap getTexture(int index) {
        return m_aTextures.get(index);
    }
}
