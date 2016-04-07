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
import com.rzsavilla.onetapgame.model.AABB;
import com.rzsavilla.onetapgame.model.AnimatedSprite;
import com.rzsavilla.onetapgame.model.Elapsed;
import com.rzsavilla.onetapgame.model.InputHandler;
import com.rzsavilla.onetapgame.model.Launcher;
import com.rzsavilla.onetapgame.model.Sprite;
import com.rzsavilla.onetapgame.model.TextureHandler;
import com.rzsavilla.onetapgame.model.Vector2D;
import com.rzsavilla.onetapgame.model.Vector2Di;

public class GameSurfaceView extends SurfaceView implements Runnable, View.OnTouchListener{
    private final static int    MAX_FPS = 60;                   // desired fps
    private final static int    MAX_FRAME_SKIPS = 3;            // maximum number of frames to be skipped
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
    AABB leftBox = new AABB();
    AABB rightBox = new AABB();

    AnimatedSprite mon1 = new AnimatedSprite();
    Sprite mon2 = new Sprite();
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
        leftBox.setPosition(screenSize.x / 4, screenSize.y / 2);
        rightBox.setPosition(screenSize.x / 2, screenSize.y / 2);

        System.out.println("Init");
        textures = new TextureHandler();
        textures.setContext(getContext());
        textures.setScreenSize(screenSize);
        textures.setScale(m_vScreenScale);

        textures.loadBitmap(R.drawable.cannon);
        textures.loadBitmap(R.drawable.soldier_spritesheet);
        textures.loadBitmap(R.drawable.grass);
        textures.loadBitmap(R.drawable.warrior);

        cannon.sprite.setTexture(textures.getTexture(0));
        cannon.setPosition(screenSize.x / 2, screenSize.y);
        cannon.setOrigin(cannon.sprite.getSize().x / 2, cannon.sprite.getSize().y / 2);
        elapsed.restart();

        left.x = -screenSize.x;
        right.x = screenSize.x;

        mon1.setPosition(screenSize.x / 2, screenSize.y / 4);
        mon1.setSpriteSheet(textures.getTexture(1), new Vector2Di(250, 250), new Vector2Di(4, 1));
        mon2.setPosition(screenSize.x / 2, screenSize.y / 2);
        mon2.setTexture(textures.getTexture(3));
        mon2.setScale(4, 4);
        System.out.println("Finish");
        //mon2.setVelocity(0, 100);
        //mon1.setVelocity(0,-100);
    }

    //Update
    Vector2D screenPos = new Vector2D(0.0f,0.0f);
    float screenTargetX = 0;
    private void updateCanvas() {
        cannon.update(m_kfTimeStep);

        if (m_bLaneChanging) {
            float fSpeed = 500.f;
            //Move
            float fDistance;
            if (bRight) {
                screenPos.x += fSpeed * m_kfTimeStep;
                fDistance = screenTargetX - screenPos.x;
            } else {
                screenPos.x -= fSpeed * m_kfTimeStep;
                fDistance =  screenPos.x -(screenTargetX);
            }

            Log.d("Distance",Float.toString(fDistance));

            if (fDistance <= 0) {
                m_bLaneChanging = false;
                screenPos.x = screenTargetX;
            }
            leftBox.setPosition(screenPos.x + screenSize.x / 4,leftBox.getPosition().y);
            rightBox.setPosition(screenPos.x + screenSize.x / 2,leftBox.getPosition().y);
        }
        //Log.d("Right?",Boolean.toString(bRight));
        mon1.update();
        mon1.moveUpdate(m_kfTimeStep);
        mon2.moveUpdate(m_kfTimeStep);
        if (mon1.getPosition().y < 50) {
            mon1.setPosition(mon1.getPosition().x, screenSize.y / 2);
        }
        if (mon2.getPosition().y > screenSize.y / 2) {
            mon2.setPosition(mon2.getPosition().x , 0);
        }

        if (mon1.collision(mon2.getBB())) {
            //mon1.setPosition(mon1.getPosition().x,screenSize.y - 300);
            //mon2.setPosition(mon2.getPosition().x,0);
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
        c.translate(-screenPos.x, -screenPos.y);
        c.drawRect(-screenSize.x, 0, screenSize.x * 2, screenSize.y, pShader);
        ////!!!!!!!!!!canvas.translate();
        //Objects

        if (!m_bLaneChanging) {
            leftBox.draw(p, c);
            rightBox.draw(p, c);
        }

        cannon.draw(p, c);
        mon1.draw(p,c);
        mon2.draw(p,c);
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
        input.setEvent(event,screenPos);         //Tap position relative to canvas position;
        Log.d("Touch x",Float.toString(input.getTapPos().x));
        if (false) {
            if (leftBox.intersect(input.getTapPos())) {
                System.out.println("Left");
                moveLeft();

            } else if (rightBox.intersect(input.getTapPos())) {
                System.out.println("Right");
                moveRight();
            }
        }
        mon1.setPosition(input.getTapPos());
    }

    public boolean onTouch(View view,MotionEvent event) {
        return true;
    }

    private boolean moveLeft() {
        if (screenPos.x > left.x) {             //Check if on left lane
            Log.d("X:",Float.toString(screenPos.x));
            if (screenPos.x  <= center.x) {                //Check if on center lane
                screenTargetX = left.x;         //Can move to the left lane
            } else{                                //On Right Lane
                screenTargetX = center.x;       //Can move to center lane
            }
            bRight = false;
            Log.d("target Left:",Float.toString(screenTargetX));
            m_bLaneChanging = true;
            return true;
        }
        return false;
    }

    private boolean moveRight() {
        if (screenPos.x < right.x) {                                          //Check if on right lane, therefore cannot move right
            if (screenPos.x >= center.x) {                                    //Check if on center lane, therefore can move to right lane
                screenTargetX = right.x;
            } else {                                                            //On Left Lane can move to center lane
                screenTargetX = center.x;
            }
            Log.d("target Right:",Float.toString(screenTargetX));
            bRight = true;
            m_bLaneChanging = true;
            return true;
        }
        return false;
    }
}
