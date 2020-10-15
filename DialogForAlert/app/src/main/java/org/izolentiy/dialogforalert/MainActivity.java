package org.izolentiy.dialogforalert;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowAlert(View view) {
        AlertDialog.Builder alertBuilder =
                new AlertDialog.Builder(MainActivity.this);

        // Set the dialog title and message
        alertBuilder.setTitle("Alert");
        alertBuilder.setMessage("Click OK to continue, or Cancel to stop:");

        // Add the dialog buttons
        alertBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked OK button
                        Toast.makeText(getApplicationContext(),
                                "Pressed OK",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        alertBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User clicked canceled the dialog
                        Toast.makeText(getApplicationContext(),
                                "Pressed Cancel",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        // Create and show AlertDialog
        alertBuilder.show();
    }
}
