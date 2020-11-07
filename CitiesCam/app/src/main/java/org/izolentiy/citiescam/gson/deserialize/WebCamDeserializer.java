package org.izolentiy.citiescam.gson.deserialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.izolentiy.citiescam.model.WebCam;

import java.lang.reflect.Type;

public class WebCamDeserializer implements JsonDeserializer<WebCam> {
    @Override
    public WebCam deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        WebCam webCam = new WebCam();
        webCam.setImageUrl(jsonObject.get("preview").getAsString());

        return webCam;
    }
}
