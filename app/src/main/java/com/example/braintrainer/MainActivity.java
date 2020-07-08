package com.example.braintrainer;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.gridlayout.widget.GridLayout;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Button buttonStart;
    private Button resetButton;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private TextView timerTextView;
    private TextView scoreTextView;
    private TextView sumTextView;
    private TextView resultTextView;
    private TextView finalTextView;
    private ArrayList<Integer> answers = new ArrayList<Integer>();
    private int locationOfCorrectAnswer;
    private int score = 0;
    private int numberOfQuestions = 0;
    private boolean finished = false;

    /*
    * Drücken auf Antwortbutton
    *
    * */
    public void chooseAnswer(View view) {

        if (finished == false) {
            if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
                resultTextView.setText("Richtig!");
                score++;
            } else {
                resultTextView.setText("Falsch!");
            }

            numberOfQuestions++;
            scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            generateQuestion();

        } else {
            scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
        }

    }

    /*
    * Neue Rechenaufgabe erstellen
    *
    * */
    public void generateQuestion() {

        // Rechenaufgabe erstellen
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b) + " = ?");
        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;
        answers.clear();

        for (int i = 0; i < 4; i++) {

            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);
            } else {
                incorrectAnswer = rand.nextInt(41);

                while (incorrectAnswer == a + b) {
                    incorrectAnswer = rand.nextInt(41);
                }

                answers.add(incorrectAnswer);
            }
        }

        // Buttonbelegung
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    /**
     * Nach Los gehts Button sollen Elemente angezeigt werden
     *
     */

    public void showElements(View view) {
        buttonStart.setVisibility(View.INVISIBLE);
        gridLayout.setVisibility(View.VISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        sumTextView.setVisibility(View.VISIBLE);

        playAgain(findViewById(R.id.resetButton));
        //resultTextView.setVisibility(View.VISIBLE);
        //finalTextView.setVisibility(View.VISIBLE);
        //resetButton.setVisibility(View.VISIBLE);
    }

    /**
     * Initial sollen Elemente ausgeblendet sein
     *
     */

    public void hideElements() {
        gridLayout.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
        sumTextView.setVisibility(View.INVISIBLE);
        finalTextView.setVisibility(View.INVISIBLE);
        resetButton.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);
    }

    /*
    * Reset-Knopf bzw. Spiel starten Button
    *
    * */
    public void playAgain(final View view) {

        score = 0;
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        resultTextView.setText("");
        resetButton.setVisibility(View.INVISIBLE);
        finished = false;

        generateQuestion();

        new CountDownTimer(60100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }
            public void onFinish(){
                timerTextView.setText("0s");
                resultTextView.setVisibility(View.VISIBLE);
                resultTextView.setText("Dein Ergebnis: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                resetButton.setVisibility(View.VISIBLE);
                finished = true;

            }
        }.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        gridLayout = (GridLayout)findViewById(R.id.gridLayout2);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        sumTextView = (TextView) findViewById(R.id.sumTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        finalTextView = (TextView) findViewById(R.id.finalTextView);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        resetButton = (Button) findViewById(R.id.resetButton);
        hideElements();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
