package org.izolentiy.citiescam.webcams;

import android.net.Uri;
import android.util.Log;

import org.izolentiy.citiescam.BuildConfig;

import java.net.MalformedURLException;
import java.util.Locale;

/**
 * Константы для работы с Webcams API
 */
public final class Webcams {
    private static final String SCHEME = "https";
    private static final String AUTHORITY = "api.windy.com";
    private static final String PATH_PARAM = "/api/webcams/v2/list/nearby=%f,%f,%f";

    private static final String PARAM_KEY = "key";
    private static final String API_KEY = BuildConfig.API_KEY;

    private static final String PARAM_SHOW = "show";
    private static final String SHOW_ARGS = "webcams:image";

    /**
     * Возвращает URL для выполнения запроса Webcams API для получения
     * информации о веб-камерах рядом с указанными координатами в формате JSON.
     */
    public static String createNearbyUrl(double lat, double lng, double radius)
            throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .authority(AUTHORITY)
                .path(String.format(Locale.US, PATH_PARAM, lat, lng, radius))
                .appendQueryParameter(PARAM_SHOW, SHOW_ARGS)
                .appendQueryParameter(PARAM_KEY, API_KEY);
        Log.d("JSONResult", builder.build().toString());
        return builder.build().toString();
    }

    private Webcams() {}
}
