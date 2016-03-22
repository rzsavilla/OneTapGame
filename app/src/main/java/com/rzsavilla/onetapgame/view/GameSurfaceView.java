package com.rzsavilla.onetapgame.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.rzsavilla.onetapgame.R;
import com.rzsavilla.onetapgame.model.Elapsed;
import com.rzsavilla.onetapgame.model.InputHandler;
import com.rzsavilla.onetapgame.model.Launcher;
import com.rzsavilla.onetapgame.model.TextureHandler;
import com.rzsavilla.onetapgame.model.Vector2D;
import com.rzsavilla.onetapgame.model.Vector2Di;

public class GameSurfaceView extends SurfaceView implements Runnable, View.OnTouchListener{
    private final static int    MAX_FPS = 60;                   // desired fps
    private final static int    MAX_FRAME_SKIPS = 10;            // maximum number of frames to be skipped
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS;  // the frame period

    private long beginTime;                                     // the time when the cycle began
    private long timeDiff;                                      // the time it took for the cycle to execute
    private int sleepTime;                                      // ms to sleep
    private int framesSkipped;                                  // number of frames being skipped
    private long timer = 0;
    private static float m_kfTimeStep = 1.0f / MAX_FPS;

    private SurfaceHolder holder;
    private Thread t = null;
    private boolean ok = false;
    private Paint p = new Paint();
    private Canvas c = new Canvas();
    private Bitmap launcherSprite;


    /////////////Screen//////////////////
    private Point screenSize;
    private boolean m_bLaneChanging = false;                   //Screen is moving to different lane
    private boolean m_bScreenDirection = false;                //Direction in which screen is moving. false = left, true = right;
    private Vector2D m_vScreenScale = new Vector2D();
    //////////////User Inputs/////////////
    private boolean m_bTap;
    InputHandler input = new InputHandler();

    //////////////OBJECTS/////////////////////
    Launcher cannon = new Launcher();

    ////////////BMP///////////////////

    private TextureHandler textures;



    //Constructor
    public GameSurfaceView(Context context) {
        super(context);
        holder = getHolder();
        init();
    }

    public GameSurfaceView(Context context ,Point ScreenS) {
        super(context);
        holder = getHolder();
        screenSize = ScreenS;
        init();
    }

    private  boolean bRight = true;
    Elapsed elapsed = new Elapsed();
    public void init() {
        textures = new TextureHandler();
        textures.setContext(getContext());
        textures.setScreenSize(screenSize);
        textures.setScale(m_vScreenScale);

        textures.loadBitmap(R.drawable.cannon);
        textures.loadBitmap(R.drawable.soldier_spritesheet);
        textures.loadBitmap(R.drawable.grass);

        cannon.sprite.setTexture(textures.getTexture(0));
        cannon.setPosition(screenSize.x / 2, screenSize.y);
        cannon.setOrigin(cannon.sprite.getSize().x / 2, cannon.sprite.getSize().y / 2);
        elapsed.restart();
    }

    //Update
    Vector2D screenPos = new Vector2D();
    private void updateCanvas() {
        cannon.update(m_kfTimeStep);
        if (elapsed.getElapsed() > 0) {
            elapsed.restart();
            float fSpeed = 1000.f * m_kfTimeStep;
            if (bRight) {
                screenPos.x += fSpeed;
            } else {
                screenPos.x -= fSpeed;
            }

        }
    }

    boolean bShaderSet = false;
    Paint pShader = new Paint();

    public void drawCanvas() {
        c.drawARGB(255, 0, 0, 0);
        if (!bShaderSet) {
            pShader.setShader(new BitmapShader(textures.getTexture(2), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            bShaderSet = true;
        }
        //Background
        //c.translate(screenPos.x,screenPos.y);
        c.drawRect(-screenSize.x, 0, screenSize.x * 2, screenSize.y, pShader);
        ////!!!!!!!!!!canvas.translate();
        //Objects

        cannon.draw(p, c);
    }
    public  void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        while (ok) {
            if (!getHolder().getSurface().isValid()) {
                continue;
            }
            c = getHolder().lockCanvas();
            synchronized (holder) {
                beginTime = System.currentTimeMillis();
                framesSkipped = 0;

                input.update();
                this.updateCanvas();
                drawCanvas();

                timeDiff = System.currentTimeMillis() - beginTime;
                //Log.d("TimeDiff",Long.toString(System.currentTimeMillis()));
               sleepTime = (int) (FRAME_PERIOD - timeDiff);
               if (sleepTime > 0) {
                   try {
                       Thread.sleep(sleepTime);
                   } catch (InterruptedException e) {
                       System.err.println("Exception: " + e.getMessage());
                   }
               }
               while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                   this.updateCanvas();
                   sleepTime += FRAME_PERIOD;
                   framesSkipped++;
               }
            }
            holder.unlockCanvasAndPost(c);
        }
    }

    public void pause() {
        ok = false;
        while (true) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        t = null;
    }

    public void resume() {
        ok = true;
        t = new Thread(this);
        t.start();
    }

    public void tapUpdate(MotionEvent event) {
        input.setEvent(event);
        if (bRight) {
            bRight = false;
        } else {
            bRight = true;
        }

    }

    public boolean onTouch(View view,MotionEvent event) {
        return true;
    }

    private boolean changeLaneLeft() {
        return false;
    }

    private boolean changeLaneRight() {
        return false;
    }
}
