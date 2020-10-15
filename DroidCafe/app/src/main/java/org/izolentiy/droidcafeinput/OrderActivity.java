package org.izolentiy.droidcafeinput;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {
    private TextView orderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        // get intent that invoked this activity
        Intent intent = getIntent();
        // extract data from intent
        if(intent.getStringExtra(MainActivity.EXTRA_ORDER) != null) {
            String order = intent.getStringExtra(MainActivity.EXTRA_ORDER);
            Log.i("OrderTAG", order);
            // init text view and set message to it
            orderView = findViewById(R.id.order_tv);
            orderView.setText(order);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button checked now?
        boolean checked = ((RadioButton)view).isChecked();
        // Check which radio button was clicked.
        switch (view.getId()) {
            case R.id.same_day:
                if(checked)
                    // Same day service
                    displayToast(getString(R.string.same_day_text));
                break;
            case R.id.next_day:
                if(checked)
                    // Next day delivery
                    displayToast(getString(R.string.next_day_text));
                break;
            case R.id.pick_up:
                if(checked)
                    // Pick up service
                    displayToast(getString(R.string.pick_up_text));
                break;
            default:
                // Do anything
                break;
        }
    }

    public void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
