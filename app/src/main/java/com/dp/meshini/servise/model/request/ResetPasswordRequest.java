package com.dp.meshini.servise.model.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResetPasswordRequest implements Serializable {

    @SerializedName("token")
    private String token;

    @SerializedName("login")
    private String login;

    @SerializedName("password")
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

}
