package org.izolentiy.pickerfordate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), getString(R.string.datepicker));
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month + 1);
        String year_string = Integer.toString(year);
        String day_string = Integer.toString(day);
        String dateMessage = (day_string+ "/" + month_string  + "/" + year_string);

        // Displaying the date
        Toast.makeText(this,
                getString(R.string.date) + dateMessage, Toast.LENGTH_SHORT).show();
    }
}
