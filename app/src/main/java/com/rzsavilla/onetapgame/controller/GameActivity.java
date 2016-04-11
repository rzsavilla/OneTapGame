package com.rzsavilla.onetapgame.controller;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

import com.rzsavilla.onetapgame.model.Utilites.Vector2D;
import com.rzsavilla.onetapgame.view.GameSurfaceView;

public class GameActivity extends Activity{
    private GameSurfaceView gsv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Screen Size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Point screenSize = new Point(metrics.widthPixels,metrics.heightPixels);
        Vector2D scale = new Vector2D(metrics.scaledDensity,metrics.scaledDensity);

        gsv = new GameSurfaceView(this,screenSize);
        setContentView(gsv);        //Set screen
        gsv.run();                  //Start Game Loop immediately
    }

    @Override
    protected void onPause() {
        super.onPause();
        gsv.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gsv.resume();
    }

    private float xPos = 0.0f;
    private float yPos = 0.0f;

    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:
                // finger touches the screen
                xPos = event.getX();
                yPos = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // finger moves on the screen
                xPos = event.getX();
                yPos = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                // finger leaves the screen
                break;
        }
        gsv.tapUpdate(event);
        return true;
    }
}