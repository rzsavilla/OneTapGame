package com.rzsavilla.onetapgame.controller;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.rzsavilla.onetapgame.R;
import com.rzsavilla.onetapgame.view.GameSurfaceView;

public class GameActivity extends Activity{
    private GameSurfaceView gsv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Screen Size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Point screenSize = new Point( metrics.widthPixels,metrics.heightPixels);

        gsv = new GameSurfaceView(this,screenSize);
        setContentView(gsv);
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
        gsv.pressUpdate(xPos,yPos);
        return true;
    }
}