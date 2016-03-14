package com.rzsavilla.onetapgame.controller;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rzsavilla.onetapgame.R;
import com.rzsavilla.onetapgame.view.GameSurfaceView;

public class GameActivity extends Activity {
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
}