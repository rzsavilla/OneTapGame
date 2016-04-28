package com.rzsavilla.onetapgame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Process;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.rzsavilla.onetapgame.Scene.Scene;
import com.rzsavilla.onetapgame.model.Handler.SoundHandler;
import com.rzsavilla.onetapgame.model.Utilites.Calculation;
import com.rzsavilla.onetapgame.model.Utilites.Elapsed;
import com.rzsavilla.onetapgame.model.Handler.InputHandler;
import com.rzsavilla.onetapgame.model.Handler.TextureHandler;
import com.rzsavilla.onetapgame.model.Utilites.Vector2D;


/**
 * The Game
 * Checks user input, update logic and renders the game
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

    private InputHandler m_Input = new InputHandler();

    /////////////Screen//////////////////
    private Point screenSize;
    private boolean m_bLaneChanging = false;                   //Screen is moving to different lane
    private Vector2D m_vScreenScale = new Vector2D();


    private Scene m_Scene = new Scene();

    ////////////BMP///////////////////
    private TextureHandler textures;

    ////////////SOUND///////////////////
    private SoundHandler m_Sound;

    private Context m_Context;

    /**
     * Construct sets context
     * @param context
     */
    public GameSurfaceView(Context context) {
        super(context);
        holder = getHolder();
        init();
    }

    /**
     * Constructor sets context and screen size
     * @param context
     * @param ScreenS
     */
    public GameSurfaceView(Context context ,Point ScreenS) {
        super(context);
        m_Context = context;
        holder = getHolder();
        screenSize = ScreenS;
        init();
    }

    private  boolean bRight = true;
    Elapsed elapsed = new Elapsed();

    /**
     * Initialize the game
     */
    public void init() {
        m_Sound = new SoundHandler(m_Context);
        textures = new TextureHandler();
        textures.setContext(getContext());
        textures.setScreenSize(screenSize);
        textures.setScale(m_vScreenScale);
        m_Scene.setTextureHandler(textures);
        m_Scene.initialize(screenSize.x, screenSize.y, this.getContext(), m_Sound);
        m_Sound.playSound(2);   //Start Music
    }

    //Update
    private Calculation calc;
    Vector2D screenPos = new Vector2D(0.0f,0.0f);
    float screenTargetX = 0;

    /**
     * Update Game Logic
     */
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
        m_Scene.updateInput(m_Input);
        m_Scene.update(m_kfTimeStep);
        m_Sound.update();
        m_Input.reset();
    }

    boolean bShaderSet = false;
    Paint pShader = new Paint();

    /**
     * Render the game
     */
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

    /**
     * Runs the game loop
     */
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

    /**
     * Pause Game loop
     */
    public void pause() {
        ok = false;
        try {
            m_Sound.pause();
            t.join();
        } catch (InterruptedException e) {
            Log.e("Error:","Joining thread");
        }
    }

    /**
     * Resume Game loop
     */
    public void resume() {
        m_Sound.resume();
        ok = true;
        t = new Thread(this);
        t.start();
    }

    /**
     * Update Input Handler
     * @param position
     * @param isDown
     */
    public void tap(Vector2D position, boolean isDown) {
        m_Input.updateTap(position,isDown);
    }
}