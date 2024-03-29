package com.rzsavilla.onetapgame.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

    public void openHighScore(View view) {
        Intent intentStart = new Intent(this, HighScoreActivity.class);
        startActivity(intentStart);
    }

    public void openSettings(View view) {
        Intent intentStart = new Intent(this, SettingsActivity.class);
        startActivity(intentStart);
    }
}