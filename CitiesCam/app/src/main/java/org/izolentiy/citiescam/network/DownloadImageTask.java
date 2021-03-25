package org.izolentiy.citiescam.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import org.izolentiy.citiescam.webcams.Webcams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static com.google.gson.JsonParser.parseString;

public class DownloadImageTask extends AsyncTask<Void, Void, String> {
    public interface DownloadCallback {
        void updateUi(String result);
    }

    private final double lat;
    private final double lng;
    private final double radius;

    private final DownloadCallback callback;

    public DownloadImageTask(double lat, double lng, double radius, Context context) {
        this.lat = lat;
        this.lng = lng;
        this.radius = radius;
        callback = (DownloadCallback)context;
    }

    @Override
    protected String doInBackground(Void... args) {
        String webcamJSONString = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            // Url request
            URL requestUrl = new URL(Webcams.createNearbyUrl(lat, lng, radius));
            // Open Url connection
            urlConnection = (HttpURLConnection)requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Incoming stream connection
            reader = new BufferedReader(new InputStreamReader
                    (urlConnection.getInputStream(), StandardCharsets.UTF_8));

            // Read the input while it is there
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            webcamJSONString = builder.toString();

            // Print pretty JSON in log
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement je = parseString(webcamJSONString);
            webcamJSONString = gson.toJson(je);
            Log.d("JSONResult", webcamJSONString);

            if (builder.length() == 0) return null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();  // Close connection
        }

        return webcamJSONString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        callback.updateUi(jsonString);
    }

    /*private String fetchJSONObject() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Webcams.createNearbyUrl(lat, lng, 200))
                .get()
                .addHeader("x-windy-key", BuildConfig.apiKey)
                .build();

        try(Response response = client.newCall(request).execute()) {
            Log.d("RESPONSE", response.body().string());
            return response.body().string();
        }
    }*/

}
