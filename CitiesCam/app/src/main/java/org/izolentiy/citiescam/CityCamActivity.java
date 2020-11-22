package org.izolentiy.citiescam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.izolentiy.citiescam.gson.jsonresponse.JsonResponse;
import org.izolentiy.citiescam.model.City;
import org.izolentiy.citiescam.model.WebCam;
import org.izolentiy.citiescam.network.DownloadImageTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
    private List<WebCam> webCamList;
    private DownloadImageTask downloadImageTask;
    private ImageView camImageView;
    private ProgressBar progressView;
    private TextView camTitleView;

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
        camTitleView = (TextView) findViewById(R.id.cam_title);

        // Show progress view
        progressView.setVisibility(View.VISIBLE);

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
            Gson gson = new Gson();
            JsonResponse response = gson.fromJson(jsonString, JsonResponse.class);
            Log.d(JSON_TAG, String.valueOf(response.getResult().getLimit()));

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray webCamsArray = jsonObject
                    .getJSONObject("result").getJSONArray("webcams");

            // Initialize the variables used for the parsing loop
            int i = 0;
            while (i < webCamsArray.length() && (imageUrl == null && title == null)) {
                // Get the current item information.
                JSONObject webCam = webCamsArray.getJSONObject(i);

                // Get the title of the camera
                title = webCam.getString("title");
                // Extract an image url from image json object
                JSONObject image = webCam.getJSONObject("image");
                imageUrl = image.getJSONObject("current").getString("preview");

                Log.d(JSON_TAG, title +": "+ imageUrl);
                i++;
            }
        } catch (JsonParseException | JSONException e) {
            Log.d(JSON_TAG, e.toString());
        }

        // Check if there is result
        if (title == null || imageUrl == null) {
            title = "No camera found";
        } else {
            // Put the camera image in to view
            Glide.with(this).load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .onlyRetrieveFromCache(false)
                    .into(camImageView);
        }
        camTitleView.setText(title);  // Put the title text in to view
        progressView.setVisibility(View.GONE);  // Hide the progressBar
    }

    @Override
    protected void onDestroy() {
        if (downloadImageTask != null) {
            downloadImageTask.cancel(true);
            downloadImageTask = null;
        }
        super.onDestroy();
    }

    private static final String JSON_TAG = "JSON_TAG";
    private static final String TAG = "CityCam";
}
