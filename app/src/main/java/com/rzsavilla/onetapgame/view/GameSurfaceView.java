package com.rzsavilla.onetapgame.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.rzsavilla.onetapgame.model.CircleShape;
import com.rzsavilla.onetapgame.model.RectangleShape;

public class GameSurfaceView extends SurfaceView implements Runnable {
    private final static int    MAX_FPS = 50;                   // desired fps
    private final static int    MAX_FRAME_SKIPS = 5;            // maximum number of frames to be skipped
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS;  // the frame period

    private long beginTime;                                     // the time when the cycle began
    private long timeDiff;                                      // the time it took for the cycle to execute
    private int sleepTime;                                      // ms to sleep
    private int framesSkipped;                                  // number of frames being skipped

    private SurfaceHolder holder;
    private Thread t = null;
    private boolean ok = false;
    private Paint paint = new Paint();
    private Bitmap launcherSprite;


    //////////////OBJECTS/////////////////////
    CircleShape ball;
    RectangleShape box;

    //Constructor
    public GameSurfaceView(Context context) {
        super(context);
        holder = getHolder();
        init();
    }

    public GameSurfaceView(Context context ,Point ScreenS) {
        super(context);
        holder = getHolder();
        init();
    }

    public void init() {
        ball = new CircleShape(100.0f,100.0f,100.0f, Color.RED);
        box = new RectangleShape(100.0f,300.0f,200.0f,200.0f,Color.GREEN);
    }

    //Update
    private void updateCanvas() {

    }

    public void drawCanvas(Canvas canvas) {
        canvas.drawARGB(255, 255, 255, 255);
        ball.draw(paint,canvas);
        box.draw(paint,canvas);
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

                this.updateCanvas();
                this.drawCanvas(c);
                timeDiff = System.currentTimeMillis() - beginTime;
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
}
