package com.dp.meshini.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class ChangeLanguageRequest {

    @SerializedName("language")
    private String appLanguage;

    public String getAppLanguage() {
        return appLanguage;
    }

    public void setAppLanguage(String appLanguage) {
        this.appLanguage = appLanguage;
    }
}
