package com.algonquincollege.zhou0121.hilo;

/**
 * Hilo Game Android app
 *
 * Author: Shenglin Zhou (zhou0121@algonquinlive.com)
 *
 * Date: 2017/10/2
 **/

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;



public class MainActivity extends Activity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";


    // Dialog
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // guessing game

    // set game turns to 0, users can begin with 0 when launching the app
    public int counts = 0;
    // give the random number a value
    public int theNumber = 0;
    // create a new number between 1 to 1000
    Random rdm = new Random();
    boolean exp = false;
    public int getNumber(boolean newNum) {
        // set max and min number(1-1000)
        int min = 1;
        int max = 1000;
        theNumber = rdm.nextInt(max) + min;
        counts = 0;
        exp = false;

        return theNumber;
    }
    // give a random number whil it doesn't exist
    public int getNumber() {
        if (theNumber != 0) {
            return theNumber;
        } else {
            return getNumber(true);
        }
    }


    // if the user takes 5 or less guesses, display a Toast message: "Superior win!"
    // if the user takes 6 to 10 guesses, display a Toast message: "Excellent win!"
    // the guess button will only display a Toast message: "Please Reset!" while the user takes more than 10 guesses

    public void winType(int userGuess) {
        // if users win the game
        counts++;
        if (userGuess == theNumber) {
            exp = true;
            if (counts <= 5) {
                Toast.makeText(getApplicationContext(), "Superior win!", Toast.LENGTH_SHORT).show();
                return;
            } else if (counts >= 6 && counts <= 10) {
                Toast.makeText(getApplicationContext(), "Excellent win!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // if users doesn't win the game
        else if (userGuess < theNumber) {
            // if number is too low
            Toast.makeText(getApplicationContext(), "Too low!", Toast.LENGTH_SHORT).show();
        }

        else if (userGuess > theNumber) {
            // if number is too high
            Toast.makeText(getApplicationContext(), "Too high!", Toast.LENGTH_SHORT).show();
        }

        if (theNumber == 0) {
            getNumber(true);
            winType(userGuess);
        }
        if (counts > 10 || exp) {
            Toast.makeText(getApplicationContext(), "Please Reset!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button guessButton = findViewById(R.id.btnGuess);
        final Button resetButton = findViewById(R.id.btnReset);
        final EditText guessEditText = findViewById(R.id.textGuess);




        // add click listener for guess button
        guessButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String guessText = guessEditText.getText().toString();
        // make sure users use actually digits to guess
                if (!guessText.isEmpty()) {
                    for (int i = 0; i < guessText.length(); i++) {
                        if (!Character.isDigit(guessText.charAt(i))) {
                            guessEditText.setError("Sorry! We allowed digits only");
                            guessEditText.requestFocus();
                            return;
                        }
                    }

        // make sure guessing number is between 1-1000
                    if (guessText.length() >= 4) {
                        if (Integer.parseInt(guessText) > 1000) {
                            guessEditText.setError("Please use a number between 1 and 1000");
                            guessEditText.requestFocus();
                            return;
                        }
                    }

                    winType(Integer.parseInt(guessText));
                } else {
                    guessEditText.setError("You need a number at least!");
                    guessEditText.requestFocus();
                }
            }
        });

        // add click listener for reset button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNumber(true);
            }
        });

        // add long click listener to show theNumber
        resetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(), Integer.toString(getNumber()), Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }


}

