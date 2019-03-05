package com.dp.meshini.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class ForgetPasswordRequest {
    @SerializedName("login")
    private String login;

    @SerializedName("code")
    private String code;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
