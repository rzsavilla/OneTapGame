package com.rzsavilla.onetapgame.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rzsavilla.onetapgame.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Go to Splash Screen
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }
}
