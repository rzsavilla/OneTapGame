package com.rzsavilla.onetapgame.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.rzsavilla.onetapgame.model.CircleShape;
import com.rzsavilla.onetapgame.model.InputHandler;
import com.rzsavilla.onetapgame.model.Projectile;
import com.rzsavilla.onetapgame.model.ProjectileHandler;
import com.rzsavilla.onetapgame.model.RectangleShape;
import com.rzsavilla.onetapgame.model.Vector2D;

import java.util.Timer;

public class GameSurfaceView extends SurfaceView implements Runnable, View.OnTouchListener{
    private final static int    MAX_FPS = 60;                   // desired fps
    private final static int    MAX_FRAME_SKIPS = 3;            // maximum number of frames to be skipped
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS;  // the frame period

    private long beginTime;                                     // the time when the cycle began
    private long timeDiff;                                      // the time it took for the cycle to execute
    private int sleepTime;                                      // ms to sleep
    private int framesSkipped;                                  // number of frames being skipped
    private long timer = 0;
    private static float kTimeStep = 1.0f / (float) MAX_FPS;

    private Timer clock;

    private SurfaceHolder holder;
    private Thread t = null;
    private boolean ok = false;
    private Paint paint = new Paint();
    private Bitmap launcherSprite;
    private Point screenSize;

    //////////////User Inputs/////////////
    private boolean m_bTap;
    InputHandler input = new InputHandler();

    //////////////OBJECTS/////////////////////
    CircleShape ball;
    RectangleShape box;
    Projectile proj = new Projectile();
    ProjectileHandler gun = new ProjectileHandler();

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

    public void init() {
        ball = new CircleShape(200.0f,100.0f,100.0f, Color.RED);
        box = new RectangleShape(screenSize.x / 2,screenSize.y / 2,200.0f,200.0f,Color.GREEN);
        timer = System.currentTimeMillis();

        proj.setTarget(300.0f,500.0f);

        gun.setPosition(screenSize.x / 2, screenSize.y - (gun.getSize().y * 2));
    }

    int iCounter = 0;
    double h = 0.001;
    float fSpeed = 2000.f;
    //Update
    private void updateCanvas() {
        //float timeStep = (float) timeDiff / 1000;             //Variable timestep jumpy!!!
        if (ball.getPosition().y > screenSize.y) {
            ball.setPosition(ball.getPosition().x, -50.0f);
        }

        ball.setPosition(ball.getPosition().x, (ball.getPosition().y + (fSpeed * kTimeStep)));
        proj.update(kTimeStep);

        if (input.isDown()) {
            gun.shoot(input.getTapPos());
        }
        gun.update(kTimeStep);
    }

    public void drawCanvas(Canvas canvas) {
        canvas.drawARGB(255, 255, 255, 255);
        ball.draw(paint, canvas);
        box.draw(paint, canvas);
        proj.draw(paint, canvas);

        gun.draw(paint, canvas);
        gun.drawProj(paint, canvas);
    }
    public  void run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        while (ok) {
            if (!getHolder().getSurface().isValid()) {
                continue;
            }
            Canvas c = getHolder().lockCanvas();
            synchronized (holder) {
                beginTime = System.currentTimeMillis();
                framesSkipped = 0;

                input.update();
                this.updateCanvas();
                drawCanvas(c);

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
    }

    public boolean onTouch(View view,MotionEvent event) {
        Log.d("Touch","Yes");
        return true;
    }
}
