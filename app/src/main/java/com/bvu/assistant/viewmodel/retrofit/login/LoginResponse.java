package com.bvu.assistant.viewmodel.retrofit.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("statusCode")
    @Expose
    private int statusCode;

    @SerializedName("isLoginSuccess")
    @Expose
    private Boolean isLoginSuccess;

    @SerializedName("sessionId")
    @Expose
    private String sessionId;

    @SerializedName("location")
    @Expose
    private String location;


    public LoginResponse(int statusCode, Boolean isLoginSuccess, String sessionId, String location) {
        this.statusCode = statusCode;
        this.isLoginSuccess = isLoginSuccess;
        this.sessionId = sessionId;
        this.location = location;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getLoginSuccess() {
        return isLoginSuccess;
    }

    public void setLoginSuccess(Boolean loginSuccess) {
        isLoginSuccess = loginSuccess;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
