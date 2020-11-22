package org.izolentiy.citiescam.gson.jsonresponse;

public class JsonWebCam {
    private int id;
    private String status;
    private String title;
    private JsonImage jsonImage;

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JsonImage getJsonImage() {
        return jsonImage;
    }

    public void setJsonImage(JsonImage jsonImage) {
        this.jsonImage = jsonImage;
    }
}
