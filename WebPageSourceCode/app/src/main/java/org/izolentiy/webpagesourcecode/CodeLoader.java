package org.izolentiy.webpagesourcecode;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class CodeLoader extends AsyncTaskLoader<String> {
    public static final String TAG = "CodeLoaderTAG";

    private static final String URL_KEY = "UrlKey";

    private String mUrl;

    CodeLoader(@NonNull Context context, String urlString) {
        super(context);
        mUrl = urlString;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return downloadUrl(mUrl);
    }

    private String downloadUrl(String urlString) {
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = null;
        try {
            URL url = new URL(urlString);
            // Open the URL connection.
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();

            stream = connection.getInputStream();
            if (stream != null) {
                // Converts Stream to String
                result = readStream(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close stream and disconnect HTTPS connection.
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        if (result == null) {
            Log.d(TAG, "result is null");
        }
        return result;
    }

    /**
     * Converts the contents of an InputStream to a String
     */
    private String readStream(InputStream stream) throws IOException {
        String result = null;
        // Read InputStream using the UTF-8 charset.
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8));
        // Create a string builder to hold an incoming response.
        StringBuilder builder = new StringBuilder();
        // Read the input while it is there.
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }
        if (builder.length() == 0) {
            return null;
        }
        reader.close();
        result = builder.toString();
        return result;
    }

}
