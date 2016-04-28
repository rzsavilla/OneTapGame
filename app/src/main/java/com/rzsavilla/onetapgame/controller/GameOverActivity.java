package com.rzsavilla.onetapgame.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rzsavilla.onetapgame.R;

/**
 * Player submits their score to the High Scores board
 * Writes to txt file containing scores
 */
public class GameOverActivity extends Activity {
    float fScore;

    private TextView textScore;
    private EditText inputName;
    private Button buttonSubmit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Bundle b = getIntent().getExtras();
        if (!b.isEmpty()) {
            if (b.containsKey("score")) { fScore = b.getFloat("score"); }
        } else {
            fScore = 0;
        }
        textScore = (TextView) this.findViewById(R.id.playerFinalScore);
        inputName = (EditText) this.findViewById(R.id.inputName);
        buttonSubmit = (Button) this.findViewById(R.id.buttonSubmit);

        textScore.setText(Float.toString(fScore));

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitScore();
            }
        });
    }

    public void submitScore() {
        Intent intent = new Intent(this, HighScoreActivity.class);
        Bundle b = new Bundle();                                //Pass player name and score to HighScore
        b.putString("name", inputName.getText().toString());
        b.putFloat("score", fScore);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}