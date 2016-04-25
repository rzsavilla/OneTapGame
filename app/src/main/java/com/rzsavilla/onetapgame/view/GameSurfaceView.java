package com.rzsavilla.onetapgame.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Process;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.rzsavilla.onetapgame.R;
import com.rzsavilla.onetapgame.Scene.Scene;
import com.rzsavilla.onetapgame.model.Shapes.Collision.AABB;
import com.rzsavilla.onetapgame.model.Shapes.Collision.Circle;
import com.rzsavilla.onetapgame.model.Utilites.Calculation;
import com.rzsavilla.onetapgame.model.Utilites.Elapsed;
import com.rzsavilla.onetapgame.Sprite.Enemy.Entity;
import com.rzsavilla.onetapgame.model.Handler.InputHandler;
import com.rzsavilla.onetapgame.Scene.Launcher;
import com.rzsavilla.onetapgame.model.Handler.TextureHandler;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

/**
 * Holds and runs the game loop
 * @author rzsavilla
 */

public class GameSurfaceView extends SurfaceView implements Runnable{
    private final static int    MAX_FPS = 30;                   // desired fps
    private final static int    MAX_FRAME_SKIPS = 3;            // maximum number of frames to be skipped
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS;  // the frame period

    private long beginTime;                                     // the time when the cycle began
    private long timeDiff;                                      // the time it took for the cycle to execute
    private long sleepTime;                                      // ms to sleep
    private int framesSkipped;                                  // number of frames being skipped
    private int iFrameCount;
    private long timer = 0;
    private float m_FPS;
    private static float m_kfTimeStep = 1.0f / MAX_FPS;

    private SurfaceHolder holder;
    private Thread t = null;
    private boolean ok = false;
    private Paint p = new Paint();
    private Canvas c = new Canvas();

    /////////////Screen//////////////////
    private Point screenSize;
    private boolean m_bLaneChanging = false;                   //Screen is moving to different lane
    private Vector2D m_vScreenScale = new Vector2D();


    private Scene m_Scene = new Scene();

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

    private Vector2D center = new Vector2D();
    private Vector2D left = new Vector2D();
    private Vector2D right = new Vector2D();

    private  boolean bRight = true;
    Elapsed elapsed = new Elapsed();
    public void init() {



        System.out.println("Init");
        textures = new TextureHandler();
        textures.setContext(getContext());
        textures.setScreenSize(screenSize);
        textures.setScale(m_vScreenScale);

        //textures.loadBitmap(R.drawable.cannon);
        //textures.loadBitmap(R.drawable.soldier_spritesheet);
        //textures.loadBitmap(R.drawable.grass);
        //textures.loadBitmap(R.drawable.warrior);
        //textures.loadBitmap(R.drawable.lava);
        m_Scene.setTextureHandler(textures);
        m_Scene.initialize(screenSize.x,screenSize.y);

    }

    //Update
    private Calculation calc;
    Vector2D screenPos = new Vector2D(0.0f,0.0f);
    float screenTargetX = 0;
    private void updateCanvas() {
        if (m_bLaneChanging) {
            float fSpeed = 2000.f;
            //Move
            float fDistance;
            if (bRight) {
                screenPos.x += fSpeed * m_kfTimeStep;
                fDistance = screenTargetX - screenPos.x;
            } else {
                screenPos.x -= fSpeed * m_kfTimeStep;
                fDistance =  screenPos.x -(screenTargetX);
            }
            //Log.d("Distance", Float.toString(fDistance));
            if (fDistance <= 0) {
                m_bLaneChanging = false;
                screenPos.x = screenTargetX;
            }
        }

        m_Scene.update(m_kfTimeStep);
    }

    boolean bShaderSet = false;
    Paint pShader = new Paint();

    public void drawCanvas() {
        if (holder.getSurface().isValid()) {
            c = holder.lockCanvas();        //Canvas ready to draw

            c.drawARGB(255, 0, 0, 0);
            //if (!bShaderSet) {
            //    pShader.setShader(new BitmapShader(textures.getTexture(2), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            //    bShaderSet = true;
            //}

            //Background
            c.translate(-screenPos.x, -screenPos.y);
            //c.drawRect(-screenSize.x, 0, screenSize.x * 2, screenSize.y, pShader);

            m_Scene.draw(p,c);
            holder.unlockCanvasAndPost(c);      //Unlock canvas
        }
    }

    private boolean m_bInputUpdated = false;
    public  void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);
        while(ok) {

            beginTime = System.currentTimeMillis();
            updateCanvas();                     //Update logic
            drawCanvas();                       //Render game
            timeDiff = System.currentTimeMillis() - beginTime;      //Time elapsed

            //FPS Counts frames
            iFrameCount++;                          //Count FPS
            if (elapsed.getElapsed() > 1.f) {
                m_FPS = iFrameCount;
                //System.out.println(m_FPS);
                iFrameCount = 0;                    //Reset Frame Count
                elapsed.restart();
            }

            //FPS Limit sleep when frame limit is being passed
            sleepTime = (FRAME_PERIOD - timeDiff);
            if (sleepTime > 0) {
                //System.out.println(sleepTime);
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
    }

    public void pause() {
        ok = false;
        try {
            t.join();
        } catch (InterruptedException e) {
            Log.e("Error:","Joining thread");
        }
    }

    public void resume() {
        ok = true;
        t = new Thread(this);
        t.start();
    }

    public void tap(Vector2D position, boolean isDown) {
        InputHandler newInput = new InputHandler();
        newInput.updateTap(position,isDown);
        m_Scene.updateInput(newInput);
    }

    private boolean moveLeft() {
        if (!m_bLaneChanging) {
            if (screenPos.x > left.x) {             //Check if on left lane
                Log.d("X:", Float.toString(screenPos.x));
                if (screenPos.x <= center.x) {                //Check if on center lane
                    screenTargetX = left.x;         //Can move to the left lane
                } else {                                //On Right Lane
                    screenTargetX = center.x;       //Can move to center lane
                }
                bRight = false;
                Log.d("target Left:", Float.toString(screenTargetX));
                m_bLaneChanging = true;
                return true;
            }
        }
        return false;
    }

    private boolean moveRight() {
        if (!m_bLaneChanging) {
            if (screenPos.x < right.x) {                                          //Check if on right lane, therefore cannot move right
                if (screenPos.x >= center.x) {                                    //Check if on center lane, therefore can move to right lane
                    screenTargetX = right.x;
                } else {                                                            //On Left Lane can move to center lane
                    screenTargetX = center.x;
                }
                Log.d("target Right:", Float.toString(screenTargetX));
                bRight = true;
                m_bLaneChanging = true;
                return true;
            }
        }
        return false;
    }
}