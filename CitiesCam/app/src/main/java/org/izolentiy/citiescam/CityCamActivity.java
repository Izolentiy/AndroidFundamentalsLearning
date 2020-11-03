package org.izolentiy.citiescam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.izolentiy.citiescam.model.City;
import org.izolentiy.citiescam.network.DownloadImageTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Экран, показывающий веб-камеру одного выбранного города.
 * Выбранный город передается в extra параметрах.
 */
public class CityCamActivity extends AppCompatActivity
        implements DownloadImageTask.DownloadCallback {

    /**
     * Обязательный extra параметр - объект City, камеру которого надо показать.
     */
    public static final String EXTRA_CITY = "city";
    private static final double RADIUS = 50;

    private City city;
    private DownloadImageTask downloadImageTask;
    private ImageView camImageView;
    private ProgressBar progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        city = getIntent().getParcelableExtra(EXTRA_CITY);
        if (city == null) {
            Log.w(TAG, "City object not provided in extra parameter: " + EXTRA_CITY);
            finish();
        }

        setContentView(R.layout.activity_city_cam);
        camImageView = (ImageView) findViewById(R.id.cam_image);
        progressView = (ProgressBar) findViewById(R.id.progress);

        // Set up the name of the city in to an action bar
        getSupportActionBar().setTitle(city.name);

        progressView.setVisibility(View.VISIBLE);

        downloadImageTask = new DownloadImageTask(city.latitude, city.longitude, RADIUS, this);
        downloadImageTask.execute();

    }

    public void updateUi(String jsonString) {
        String imageUrl = null;
        String title = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray webCamsArray = jsonObject
                    .getJSONObject("result").getJSONArray("webcams");

            // Initialize the variables used for the parsing loop
            int i = 0;
            while (i < webCamsArray.length() && (imageUrl == null && title == null)) {
                // Get the current item information.
                JSONObject webCam = webCamsArray.getJSONObject(i);
                title = webCam.getString("title");
                JSONObject image = webCam.getJSONObject("image");
                imageUrl = image.getJSONObject("current").getString("preview");
                Log.d("JSON_TAG", title +": "+ imageUrl);
                i++;
            }
        } catch (JSONException e) {
            Log.d("JSON_TAG", e.toString());
        }
        // Put an camera image in to view
        // (but it looks like I exceeded the daily number of api requests)
        /* Glide.with(this).load(imageUrl).into(camImageView); */

        // URL of image with really beautiful girl
        String url = "https://images.unsplash.com/photo-1591927076671-71214f64e1a4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2000&q=80";
        Glide.with(this).load(url).into(camImageView);
        progressView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        if (downloadImageTask != null) {
            downloadImageTask.cancel(true);
            downloadImageTask = null;
        }
        super.onDestroy();
    }

    private static final String TAG = "CityCam";
}
