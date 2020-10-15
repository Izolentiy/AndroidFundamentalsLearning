package org.izolentiy.scorekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final String STATE_SCORE_1 = "Team 1 Score";
    static final String STATE_SCORE_2 = "Team 2 Score";

    private int score1 = 0;
    private int score2 = 0;

    private TextView score1_tv;
    private TextView score2_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score1_tv = findViewById(R.id.score_team1);
        score2_tv = findViewById(R.id.score_team2);

        if (savedInstanceState != null) {
            score1 = savedInstanceState.getInt(STATE_SCORE_1);
            score2 = savedInstanceState.getInt(STATE_SCORE_2);

            score1_tv.setText(String.valueOf(score1));
            score2_tv.setText(String.valueOf(score2));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        switch (nightMode) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.follow_the_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.auto_mode).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.night_mode).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.day_mode).setChecked(true);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Check if the correct item was clicked
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.follow_the_system:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.auto_mode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            case R.id.night_mode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.day_mode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
        // Recreate the activity for the theme that changed to take affect
        recreate();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the scores
        outState.putInt(STATE_SCORE_1, score1);
        outState.putInt(STATE_SCORE_2, score2);
        super.onSaveInstanceState(outState);
    }

    public void decreaseScore(View view) {
        int viewId = view.getId();
        switch (viewId){
            case R.id.decrease_team1:
                score1--;
                score1_tv.setText(String.valueOf(score1));
                break;
            case R.id.decrease_team2:
                score2--;
                score2_tv.setText(String.valueOf(score2));
                break;
        }
    }

    public void increaseScore(View view) {
        int viewId = view.getId();
        switch(viewId) {
            case R.id.increase_team1:
                score1++;
                score1_tv.setText(String.valueOf(score1));
                break;
            case R.id.increase_team2:
                score2++;
                score2_tv.setText(String.valueOf(score2));
                break;
        }
    }
}
