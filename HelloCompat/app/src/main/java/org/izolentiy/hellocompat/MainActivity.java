package org.izolentiy.hellocompat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // TextView for Hello World
    private TextView helloWorldView;
    // array of color names, these match the color resources in color.xml
    private String[] colorArray = { "red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", "black" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloWorldView = findViewById(R.id.hello_world_tv);
        // restore saved instance state (the text color)
        if(savedInstanceState != null) {
            helloWorldView.setTextColor(savedInstanceState.getInt("color"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save a current text color
        outState.putInt("color", helloWorldView.getCurrentTextColor());
    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
    public void changeColor(View view) {
        Random random = new Random();
        String colorName = colorArray[random.nextInt(20)];
        int colorResourceName = getResources().getIdentifier(
                colorName, "color", getApplicationContext().getPackageName()
        );
//        // the line below requires api 23
//        int colorRes = getResources().getColor(colorResourceName, this.getTheme());
        int colorRes = ContextCompat.getColor(this, colorResourceName);
        helloWorldView.setTextColor(colorRes);
    }
}
