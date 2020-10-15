package org.izolentiy.batterydrawables;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private LevelListDrawable batteryLevels;
    private int curr_level = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView batteryImage = findViewById(R.id.battery_image);
        batteryLevels = (LevelListDrawable) batteryImage.getDrawable();
        batteryLevels.setLevel(curr_level);
    }

    public void decreaseLevel(View view) {
        int min_level = 0;
        if (curr_level == min_level) {
            batteryLevels.setLevel(curr_level);
        } else {
            batteryLevels.setLevel(--curr_level);
        }
    }

    public void increaseLevel(View view) {
        int max_level = 7;
        if (curr_level == max_level) {
            batteryLevels.setLevel(curr_level);
        } else {
            batteryLevels.setLevel(++curr_level);
        }
    }
}
