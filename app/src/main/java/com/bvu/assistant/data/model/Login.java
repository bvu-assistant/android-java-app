package com.bvu.assistant.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    public static class Request {
        private String id;
        private String pass;
        private String ssid;

        public Request(String id, String pass, String ssid) {
            this.id = id;
            this.pass = pass;
            this.ssid = ssid;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }
    }

    public static class Response {
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


        public Response(int statusCode, Boolean isLoginSuccess, String sessionId, String location) {
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
}
