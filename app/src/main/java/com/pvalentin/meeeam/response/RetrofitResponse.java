package com.pvalentin.meeeam.response;

public class RetrofitResponse {
    private String status;
    private String message;
    private String data;
    private String token;

    public RetrofitResponse(String status, String message, String data, String token) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
