package com.dp.meshini.servise.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefaultResponse {

    @SerializedName("errors")
    private List<String> errors;
    @SerializedName("error")
    private String error;

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getError() {
        return error;
    }
}
