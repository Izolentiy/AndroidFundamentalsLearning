package org.izolentiy.citiescam.gson.jsonresponse;

import java.util.List;

public class JsonResult {
    private int offset;
    private int limit;
    private int total;
    private List<JsonWebCam> webCams[];

    // Getters and setters

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<JsonWebCam>[] getWebCams() {
        return webCams;
    }

    public void setWebCams(List<JsonWebCam>[] webCams) {
        this.webCams = webCams;
    }
}
