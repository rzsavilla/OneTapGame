package com.rzsavilla.onetapgame.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.*;
import android.os.Process;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.rzsavilla.onetapgame.R;
import com.rzsavilla.onetapgame.model.Collision.AABB;
import com.rzsavilla.onetapgame.model.Animation.AnimatedSprite;
import com.rzsavilla.onetapgame.model.Collision.Circle;
import com.rzsavilla.onetapgame.model.Elapsed;
import com.rzsavilla.onetapgame.model.Inherited.Entity;
import com.rzsavilla.onetapgame.model.InputHandler;
import com.rzsavilla.onetapgame.model.Launcher;
import com.rzsavilla.onetapgame.model.Handler.TextureHandler;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;
import com.rzsavilla.onetapgame.model.Utilites.Vector2Di;

public class GameSurfaceView extends SurfaceView implements Runnable, View.OnTouchListener{
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

    Circle circle = new Circle();
    Circle ball = new Circle();

    Entity mon1 = new Entity();
    Entity mon2 = new Entity();

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

        //leftBox.setOrigin(leftBox.getSize().x / 2, leftBox.getSize().y / 2);
        rightBox.setPosition(screenSize.x - (screenSize.x / 4), screenSize.y / 1.2f);
        leftBox.setPosition((screenSize.x / 4), screenSize.y / 1.2f);
        leftBox.setSize(200.0f, 200.0f);
        rightBox.setSize(200.0f, 200.0f);
        leftBox.setOrigin(leftBox.getSize().divide(2));
        rightBox.setOrigin(rightBox.getSize().divide(2));

        circle.setPosition(100.0f, 100.0f);
        circle.setRadius(100.0f);
        ball.setPosition(screenSize.x / 2.0f, screenSize.y / 2.0f);
        ball.setRadius(120.0f);
        ball.setColour(Color.WHITE);

        circle.setPosition(screenSize.x / 2.0f, 100.0f);
        circle.setVelocity(0.0f, 100.0f);

        circle.setMass(1.0f);
        ball.setMass(1.0f);

        circle.setVelocity(0, 1);
        ball.setVelocity(0,-1);

        System.out.println("Init");
        textures = new TextureHandler();
        textures.setContext(getContext());
        textures.setScreenSize(screenSize);
        textures.setScale(m_vScreenScale);

        textures.loadBitmap(R.drawable.cannon);
        textures.loadBitmap(R.drawable.soldier_spritesheet);
        textures.loadBitmap(R.drawable.grass);
        textures.loadBitmap(R.drawable.warrior);
        textures.loadBitmap(R.drawable.lava);

        cannon.sprite.setTexture(textures.getTexture(0));
        cannon.setPosition(screenSize.x / 2, screenSize.y);
        cannon.setOrigin(cannon.sprite.getSize().x / 2, cannon.sprite.getSize().y / 2);
        elapsed.restart();

        left.x = -screenSize.x;
        right.x = screenSize.x;

        mon1.setPosition(screenSize.x / 4, screenSize.y / 4);
        mon1.setSpriteSheet(textures.getTexture(1), new Vector2Di(250, 250), new Vector2Di(4, 1));
        mon2.setPosition(screenSize.x / 2, screenSize.y / 2);
        mon2.setSpriteSheet(textures.getTexture(1), new Vector2Di(250, 250), new Vector2Di(4, 1));
        System.out.println("Finish");
        mon2.setVelocity(0, 100);
        //mon1.setVelocity(0, 100);
        mon1.setMass(10.0f);
    }

    //Update
    Vector2D screenPos = new Vector2D(0.0f,0.0f);
    float screenTargetX = 0;
    private void updateCanvas() {
        //mon1.bb.collision(mon2.bb);
        //mon2.bb.collision(input.getMouseBB());

        //circle.collision(ball);
        //mon1.setSize(300.0f,500.0f);
        //mon1.setOrigin(150.0f, 250.0f);
        circle.impulse(mon1);

        circle.collision(mon1.bb);
        circle.impulse(ball);
        //mon1.bb.intersect(mon2.bb);
        //mon2.bb.collision(mon1.bb);
        if (input.bTap) {
            //circle.setPosition(input.getTapPos());
            if (!m_bLaneChanging) {
                if (input.m_MouseBB.getPosition().y < screenSize.y / 1.3) {
                    //cannon.rotateTowards(input.getTapPos());
                    //cannon.m_Bullets.shoot(input.getTapPos());
                    cannon.markTarget(input.m_MouseBB.getPosition());
                } else {
                    if (input.m_MouseBB.collision(leftBox)) {
                        moveLeft();
                    } else if (input.m_MouseBB.collision(rightBox)) {
                        moveRight();
                    }
                }
            }
            m_bTap = false;
        }
        //Log.d("Tap: ", Float.toString(circle.getPosition().x));

        leftBox.updateGlobalBounds();
        rightBox.updateGlobalBounds();

        cannon.update(m_kfTimeStep);

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
            rightBox.setPosition(screenSize.x - (screenSize.x / 4) + screenPos.x, screenSize.y / 1.1f);
            leftBox.setPosition((screenSize.x / 4) + screenPos.x, screenSize.y / 1.1f);
        }
        //Log.d("Right?",Boolean.toString(bRight));


        if (mon1.getPosition().y > screenSize.y - 300) {
            mon1.setPosition(mon1.getPosition().x , 0);
        }
        if (mon2.getPosition().y > screenSize.y - 300) {
            mon2.setPosition(mon2.getPosition().x , 0);
        }

        mon1.update(m_kfTimeStep);
        mon2.update(m_kfTimeStep);

        //mon1.moveUpdate(m_kfTimeStep);
        //mon2.moveUpdate(m_kfTimeStep);
        circle.moveUpdate(m_kfTimeStep);
        ball.moveUpdate(m_kfTimeStep);
    }

    boolean bShaderSet = false;
    Paint pShader = new Paint();

    public void drawCanvas() {
        if (holder.getSurface().isValid()) {

            c = holder.lockCanvas();        //Canvas ready to draw

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
                if (screenPos.x > left.x) {
                    leftBox.draw(p, c);
                }
                if (screenPos.x < right.x) {
                    rightBox.draw(p, c);
                }
            }

            cannon.draw(p, c);
            mon1.draw(p, c);
            mon2.draw(p, c);
            input.getMouseBB().draw(p, c);

            ball.draw(p, c);
            circle.draw(p, c);

            holder.unlockCanvasAndPost(c);      //Unlock canvas
        }
    }

    public  void run() {
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);
        while(ok) {
            beginTime = System.currentTimeMillis();

            //Game Loop
            input.update(screenPos);            //Update inputs
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

    public void tapUpdate(MotionEvent event) {
        input.setEvent(event, screenPos);         //Tap position relative to canvas position;
    }

    public boolean onTouch(View view,MotionEvent event) {
        return true;
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