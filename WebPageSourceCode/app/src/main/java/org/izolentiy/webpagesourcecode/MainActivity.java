package org.izolentiy.webpagesourcecode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener,
        LoaderManager.LoaderCallbacks<String> {
    public static final String TAG = "MainActivityTAG";

    private static final String URL_KEY = "UrlKey";

    private EditText mEditText;
    private Spinner mProtocol;
    private TextView mCodeHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.url);
        mProtocol = findViewById(R.id.protocol);
        mCodeHolder = findViewById(R.id.source_code);

        // Create an array adapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.protocols_array,
                android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (mProtocol != null) {
            // Set select listener
            mProtocol.setOnItemSelectedListener(this);
            // Apply the adapter to the spinner.
            mProtocol.setAdapter(adapter);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void getPageSource(View view) {
        String protocol = mProtocol.getSelectedItem().toString();
        String host = mEditText.getText().toString();
        String urlString =  protocol + host;

        // Hide keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Check internet connection
        ConnectivityManager connManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connManager != null) {
            networkInfo = connManager.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected() && host.length() != 0) {
            // Launch loader
            Bundle args = new Bundle();
            args.putString(URL_KEY, urlString);
            getSupportLoaderManager().restartLoader(0, args, this);
            mCodeHolder.setText(R.string.loading);
        } else {
            if (host.length() == 0) {
                mCodeHolder.setText(R.string.no_host);
            } else {
                mCodeHolder.setText(R.string.no_internet);
            }
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String urlString = "";
        if (args != null) {
            urlString = args.getString(URL_KEY);
        }
        return new CodeLoader(this, urlString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (data != null) {
            mCodeHolder.setText(data);
        } else {
            mCodeHolder.setText(R.string.nothing_found);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
