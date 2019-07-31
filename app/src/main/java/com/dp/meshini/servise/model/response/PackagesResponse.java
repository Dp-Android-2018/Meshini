package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.Links;
import com.dp.meshini.servise.model.pojo.PackageData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackagesResponse {

    @SerializedName("data")
    private List<PackageData> packages;

    @SerializedName("links")
    private Links links;

    public List<PackageData> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageData> packages) {
        this.packages = packages;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
