package org.izolentiy.citiescam.gson.jsonresponse;

public class JsonResponse {
    private String status;
    private JsonResult result;

    // Getters and setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JsonResult getResult() {
        return result;
    }

    public void setResult(JsonResult result) {
        this.result = result;
    }
}
