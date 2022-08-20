package com.ahmadabuhasan.volleygson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseStatus {

    @SerializedName("status_kode")
    @Expose
    private int status_code;
    @SerializedName("status_pesan")
    @Expose
    private String status_message;

    public ResponseStatus(int status_code, String status_message) {
        this.status_code = status_code;
        this.status_message = status_message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }
}