package com.rzsavilla.onetapgame.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.rzsavilla.onetapgame.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
    }

    public void startGame(View view) {
        Intent intentStart = new Intent(this, StartActivity.class);
        startActivity(intentStart);
    }
}
