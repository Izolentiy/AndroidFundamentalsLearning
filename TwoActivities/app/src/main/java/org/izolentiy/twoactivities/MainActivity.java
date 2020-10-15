package org.izolentiy.twoactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int TEXT_REQUEST = 1;
    public static final String EXTRA_MESSAGE = "org.izolentiy.twoactivities.extra.MESSAGE";

    private static final String LOG_TAG = MainActivity.class.getSimpleName()+"TAG";
    private EditText mMessage;
    private TextView mHeadTextView;
    private TextView mMessageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "--------");
        Log.d(LOG_TAG, "onCreate");
        setContentView(R.layout.activity_main);
        mHeadTextView = findViewById(R.id.header_main);
        mMessageView = findViewById(R.id.message_main);
        mMessage = findViewById(R.id.edit_text_main);

        // Restore the state
        if(savedInstanceState != null) {
            boolean isVisible = savedInstanceState.getBoolean("message_visible");
            if(isVisible) {
                mHeadTextView.setVisibility(View.VISIBLE);
                mMessageView.setText(savedInstanceState.getString("message_text"));
                mMessageView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mHeadTextView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("message_visible", true);
            outState.putString("message_text", mMessageView.getText().toString());
            Log.d(LOG_TAG, "Message state saved on Bundle");
        }
    }

    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        String message = mMessage.getText().toString();
        mMessage.setText("");
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, TEXT_REQUEST);
        Log.d(LOG_TAG, "Message sent");
    }

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST)
            if (resultCode == RESULT_OK) {
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                mHeadTextView.setVisibility(View.VISIBLE);
                mMessageView.setText(reply);
                mMessageView.setVisibility(View.VISIBLE);
            }
    }
}
