package com.dp.meshini.servise.model.request;

import com.dp.meshini.view.callback.IoCustomRequest;
import com.google.gson.annotations.SerializedName;

public class LoginRequest implements IoCustomRequest {


    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    @SerializedName("device_token")
    private String deviceToken;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
