package com.dp.meshini.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class ActivatePhoneRequest {

    @SerializedName("code")
    private String code;

    @SerializedName("phone")
    private String phone;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
