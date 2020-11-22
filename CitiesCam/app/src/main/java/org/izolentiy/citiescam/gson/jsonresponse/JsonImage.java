package org.izolentiy.citiescam.gson.jsonresponse;

import java.util.HashMap;

public class JsonImage {
    private HashMap<String, String> current;
    private HashMap<String, HashMap<String, Integer>> sizes;
    private HashMap<String, String> daylight;
    private long update;

    // Getters and setters

    public HashMap<String, String> getCurrent() {
        return current;
    }

    public void setCurrent(HashMap<String, String> current) {
        this.current = current;
    }

    public HashMap<String, HashMap<String, Integer>> getSizes() {
        return sizes;
    }

    public void setSizes(HashMap<String, HashMap<String, Integer>> sizes) {
        this.sizes = sizes;
    }

    public HashMap<String, String> getDaylight() {
        return daylight;
    }

    public void setDaylight(HashMap<String, String> daylight) {
        this.daylight = daylight;
    }

    public long getUpdate() {
        return update;
    }

    public void setUpdate(long update) {
        this.update = update;
    }
}
