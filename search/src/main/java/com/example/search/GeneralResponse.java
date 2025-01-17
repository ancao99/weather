package com.example.search;

public class GeneralResponse {
    private int code;
    private String timestamp;
    private String data;

    public GeneralResponse(int code, String timestamp, String data) {
        this.code = code;
        this.timestamp = timestamp;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
// Getters and Getter

}
