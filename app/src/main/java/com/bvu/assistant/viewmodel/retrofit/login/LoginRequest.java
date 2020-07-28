package com.bvu.assistant.viewmodel.retrofit.login;

public class LoginRequest {
    private String id;
    private String pass;
    private String ssid;

    public LoginRequest(String id, String pass, String ssid) {
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
