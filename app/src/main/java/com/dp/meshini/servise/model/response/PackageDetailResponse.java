package com.dp.meshini.servise.model.response;

import com.dp.meshini.servise.model.pojo.PackageDetail;
import com.google.gson.annotations.SerializedName;

public class PackageDetailResponse {

    @SerializedName("data")
    private PackageDetail packageDetail;

    public PackageDetail getPackageDetail() {
        return packageDetail;
    }

    public void setPackageDetail(PackageDetail packageDetail) {
        this.packageDetail = packageDetail;
    }
}
