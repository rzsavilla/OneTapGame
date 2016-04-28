package com.rzsavilla.onetapgame.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.rzsavilla.onetapgame.Player;
import com.rzsavilla.onetapgame.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 */
public class HighScoreActivity extends Activity {
    //Code from file I/O lecture
    private Player[] scores = new Player[4];
    TextView name1;
    TextView name2;
    TextView name3;
    TextView score1;
    TextView score2;
    TextView score3;

    EditText inputName;
    Button buttonSubmit;
    Button buttonMenu;
    Button buttonPlayAgain;

    private boolean m_bSubmitted;

    private View.OnClickListener listener;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        //Get Value of fields
        name1 = (TextView) this.findViewById(R.id.name1);
        name2 = (TextView) this.findViewById(R.id.name2);
        name3 = (TextView) this.findViewById(R.id.name3);

        score1 = (TextView) this.findViewById(R.id.score1);
        score2 = (TextView) this.findViewById(R.id.score2);
        score3 = (TextView) this.findViewById(R.id.score3);

        buttonMenu = (Button) this.findViewById(R.id.buttonMenu);
        buttonPlayAgain = (Button) this.findViewById(R.id.buttonPlayAgain);

        loadFromFile();

        //Get any values passed to this activity
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("score") && b.containsKey("name")) {
                //Update Score
                float fScore = 0.0f;
                String sName = "-";
                fScore = b.getFloat("score");
                sName = b.getString("name");
                updateScores(sName, fScore);
            }
        }
        //Buttons pressed
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenu();
            }
        });
        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });


        loadFromFile();
        displayTable();
    }

    /**
     * Return to start activity
     */
    public void backToMenu() {
        Intent intentStart = new Intent(this, StartActivity.class);
        startActivity(intentStart);
    }

    /**
     * Start game activity
     */
    public void playAgain() {
        Intent intentPlay = new Intent(this, GameActivity.class);
        startActivity(intentPlay);
    }

    /**
     * Update values of Text view
     */
    private void displayTable() {
        name1.setText(scores[0].getName());
        name2.setText(scores[1].getName());
        name3.setText(scores[2].getName());

        score1.setText(Float.toString(scores[0].getScore()));
        score2.setText(Float.toString(scores[1].getScore()));
        score3.setText(Float.toString(scores[2].getScore()));
    }

    /**
     * Update Score and sort into top three
     * @param name
     * @param score
     */
    private void updateScores(String name, float score) {
        scores[3] = new Player(name, score);
        Arrays.sort(scores);
        //Arrays.sort(scores);
        saveToFile();
        displayTable();
    }

    /**
     * Read High Scores  txt file
     * @return Read successful
     */
    private boolean loadFromFile() {
        File root = new File(getFilesDir(), "highScores");
        if (!root.exists()) {
            root.mkdirs();
        }
        File infile = new File(root, "highScores.txt");
        if (!infile.exists()) {
            //Starting score board
            scores[0] = new Player("Player1",3.0f);
            scores[1] = new Player("Player2",2.0f);
            scores[2] = new Player("Player3",1.0f);
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(infile));
            String line;
            for(int i =0;i < 3;i++) {
                String name = "";
                float score = 0.0f;
                if ((line = br.readLine()) != null) {
                    name = line;
                }
                if ((line = br.readLine()) != null) {
                    score = Float.parseFloat(line);
                }
                scores[i] = new Player(name, score);
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Write to High Scores txt file, saving top three high scores into storage
     * @return
     */
    private boolean saveToFile()
    {
        //scores[0] = new Player("Player1",0.0f);
        //scores[1] = new Player("Player2",0.0f);
        //scores[2] = new Player("Player3",0.0f);
        // Construct string
        String strToWrite = "";
        for(int i =0;i < 3;i++)
        {
            strToWrite += scores[i].getName() + "\n";
            strToWrite += Float.toString(scores[i].getScore()) + "\n";
        }

        // Save it to a file
        try
        {
            File root = new File(getFilesDir(), "highScores");
            if (!root.exists()) {
                root.mkdirs();
            }
            File outfile = new File(root, "highScores.txt");
            FileWriter writer = new FileWriter(outfile);
            writer.append(strToWrite);
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_score, menu);
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
