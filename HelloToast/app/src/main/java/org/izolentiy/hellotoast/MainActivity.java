package org.izolentiy.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    private TextView countView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countView = findViewById(R.id.label_count);
    }

    public void showToast(View view){
        Toast toast = Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp (View view){
        try {
            ++count;
            if (countView != null)
                countView.setText(String.format(Locale.ENGLISH,"%d", count));
        } catch (Exception e) {
            Log.e("MainActivity", "Something went wrong");
        }
    }

}
