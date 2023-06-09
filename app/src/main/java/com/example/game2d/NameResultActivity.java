package com.example.game2d;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * This is the results page/activity for the <code>NameActivity</code> that
 * displays the current score out of 5 and a corresponding comment.
 * It also displays the average and highest score as a percentage that
 * persists when the app is closed and reopened.
 */
public class NameResultActivity extends AppCompatActivity { //

    TextView newscore;

    TextView comment;

    int lastScore;
    int best;
    TextView bestscore;

    TextView averagescore;

    public float percent = 0; //this is the numerical average accuracy of nameGame (in percentage)

    //
    private static final String FLOAT_VECTOR_KEY = "float_vector_key";
    //

    Button submitBtn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Sets the contentView to the related XML file and gets rid of the
         * status bar. It also initializes the files (textviews and buttons)
         * and their mechanics.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_result);

        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        newscore = (TextView) findViewById(R.id.score);
        bestscore = (TextView) findViewById(R.id.highestscore);
        comment = (TextView) findViewById(R.id.comment);

        averagescore = (TextView) findViewById(R.id.averagescore);

        // Continue button
        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent back to <code>MainActivity</code> when the
             * user clicks on the continue button.
             * @param view the button instance/click from the user when they click
             * on the continue button.
             */
            @Override
            public void onClick(View view) {
                // switch back to main activity
                Intent intent = new Intent(NameResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // Try again button
        Button again_button = (Button) findViewById(R.id.again_button);
        again_button.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates/executes the intent to the <code>NameActivity</code> which restarts it
             * so the user can play again when they click on the play again button
             * @param view the button instance/click from the user when they click on
             * on the play again button.
             */
            @Override
            public void onClick(View view) {
                // switch back to main activity
                Intent intent = new Intent(NameResultActivity.this, NameActivity.class);
                startActivity(intent);
            }
        });

        //-------------- save data to sharedPreference ----------------------
        SharedPreferences preferences = getSharedPreferences("MY_PREFS", 0);
        lastScore = preferences.getInt("lastScore", 0);
        newscore.setText((lastScore/5.0*100)+"%");
        //preferences.edit().remove("allNameScores").apply();

        // ----- this was used to store best score
        best = preferences.getInt("best", 0);
        if (lastScore > best) {
            best = lastScore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best", best);
            editor.apply();
        }
        bestscore.setText((best/5.0*100) + "%");
        //----------------------------------------------------------------------
        //comments based on scores
        if (lastScore < 3) {
            comment.setText("You must be a terrible person.");
        } else if (lastScore == 3) {
            comment.setText("Not bad, not good either");
        } else if (lastScore == 4) {
            comment.setText("Okay... don't be cocky.");
        } else if (lastScore == 5) {
            comment.setText("You cheated.");
        }

        //----
        // 1. Get a string variable from shared preferences called allNameScores
        String allNameScores = preferences.getString("allNameScores", "");

        // 2. Convert it into a vector called score_vec by breaking up each digit as an element
        Vector<Integer> score_vec = new Vector<>();
        for (int i = 0; i < allNameScores.length(); i++) {
            score_vec.add(Integer.parseInt(allNameScores.substring(i, i + 1)));
        }

        // 3. Append lastScore (a predetermined variable) to score_vec
        score_vec.add(lastScore);

        // 4. Calculate average of score_vec and store it in variable average
        int sum = 0;
        for (int score : score_vec) {
            sum += score;
        }
        double average = (double) sum / score_vec.size();
        percent = (float) (average/5.0*100);

        // 5. Convert the new score_vec back into a string and store back in allNameScores in shared Preferences
        StringBuilder stringBuilder = new StringBuilder();
        for (int score : score_vec) {
            stringBuilder.append(score);
        }
        preferences.edit().putString("allNameScores", stringBuilder.toString()).apply();
        preferences.edit().putFloat("percent", percent).apply();
        //----

        //storing a vector of scores to find average
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//
//        Set<String> floatSet = sharedPreferences.getStringSet(FLOAT_VECTOR_KEY, null);
//        List<Float> floatList = new ArrayList<>();
//
//        if (floatSet != null) {
//            for (String floatValue : floatSet) {
//                floatList.add(Float.valueOf(floatValue));
//            }
//        }
//
//        for (String item:floatSet){
//            Log.d(TAG,item);
//        }
//        floatList.add((float)lastScore);
//
//        Set<String> updatedFloatSet = new HashSet<>();
//        for (Float floatValue : floatList) {
//            updatedFloatSet.add(String.valueOf(floatValue));
//        }
//        sharedPreferences.edit().putStringSet(FLOAT_VECTOR_KEY, updatedFloatSet).apply();
//
//        float sum = 0;
//        for (Float floatValue : floatList) {
//            sum += floatValue;
//        }
//
//        //calculate average, convert to percentage, then display, then store back in shared preference
//        float average = sum / floatList.size();
//        float raw_percent = average/5*100;
//        DecimalFormat df = new DecimalFormat("##.##");
//        df.setRoundingMode(RoundingMode.CEILING);
//
//        percent = Float.parseFloat(df.format(raw_percent));
//
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putFloat("percent", percent);
//        editor.apply();


        averagescore.setText(percent + "%");

    }



}