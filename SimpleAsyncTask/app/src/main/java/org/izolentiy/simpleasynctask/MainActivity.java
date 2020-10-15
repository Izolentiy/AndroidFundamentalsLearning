package org.izolentiy.simpleasynctask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TEXT_STATE = "currentText";

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.textView1);

        if (savedInstanceState != null) {
            // Restore saved text
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of the TextView
        outState.putString(TEXT_STATE, mTextView.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void startTask(View view) {
        // Put the message in the text view
        mTextView.setText(R.string.napping_label);

        // Show the progressBar
        ProgressBar mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        // Start the AsyncTask
        new SimpleAsyncTask(mProgressBar, mTextView).execute();
    }
}
