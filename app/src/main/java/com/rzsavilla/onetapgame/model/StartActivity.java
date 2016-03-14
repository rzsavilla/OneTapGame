package com.rzsavilla.onetapgame.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rzsavilla.onetapgame.R;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void playGame(View view) {
        Intent intentStart = new Intent(this, GameActivity.class);
        startActivity(intentStart);
    }

    public void openSettings(View view) {
        Intent intentStart = new Intent(this, SettingsActivity.class);
        startActivity(intentStart);
    }
}