package com.dp.meshini.servise.model.request;

import com.google.gson.annotations.SerializedName;

public class PaymentRequest  {

    @SerializedName("room_type")
    private String roomType;

    @SerializedName("package_id")
    private int packageId;

    @SerializedName("no_of_persons")
    private int noOfPersons;

    @SerializedName("payment_type")
    private String paymentType;

    @SerializedName("total_amounts")
    private double totalAmounts;

    @SerializedName("picture")
    private String pictureUrl;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(int noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getTotalAmounts() {
        return totalAmounts;
    }

    public void setTotalAmounts(double totalAmounts) {
        this.totalAmounts = totalAmounts;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
