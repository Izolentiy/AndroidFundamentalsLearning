package org.izolentiy.webpagesourcecode;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {
    public static final String TAG = "NetworkUtilTAG";

    private String downloadUrl(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;
        try {
            // Open the URL connection.
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != connection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            stream = connection.getInputStream();
            if (stream != null) {
                // Converts Stream to String
                result = readStream(stream);
            }
        } finally {
            // Close stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        Log.d(TAG, result);
        return result;
    }

    /**
     * Converts the contents of an InputStream to a String
     */
    private String readStream(InputStream stream) throws IOException {
        String result = null;
        // Read InputStream using the UTF-8 charset.
        BufferedReader  reader = new BufferedReader(
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
        result = builder.toString();
        return result;
    }
}
