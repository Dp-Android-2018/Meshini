package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.CountryCityPojo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryCityResponse {

    @SerializedName("data")
    private List<CountryCityPojo> countryCityList;

    public List<CountryCityPojo> getCountryCityList() {
        return countryCityList;
    }

    public void setCountryCityList(List<CountryCityPojo> countryCityList) {
        this.countryCityList = countryCityList;
    }
}
