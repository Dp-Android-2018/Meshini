package com.dp.meshini.servise.model.request;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

public class CreateTripRequest implements Serializable {
    @SerializedName("country_id")
    private int countryId;

    @SerializedName("language_id")
    private int languageId;

    @SerializedName("vehicle_type")
    private String vehicleType;

    @SerializedName("pickup_lat")
    private double pickupLat;

    @SerializedName("pickup_long")
    private double pickupLong;

    @SerializedName("pickup_address")
    private String pickupAddress;

    @SerializedName("pickup_time")
    private String pickupTime;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    @SerializedName("place_ids")
    private List<Integer>placeIds;

    @SerializedName("notes")
    private String notes;

    @SerializedName("payment_method")
    private String paymentMethod;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getPickupLat() {
        return pickupLat;
    }

    public void setPickupLat(double pickupLat) {
        this.pickupLat = pickupLat;
    }

    public double getPickupLong() {
        return pickupLong;
    }

    public void setPickupLong(double pickupLong) {
        this.pickupLong = pickupLong;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getPlaceIds() {
        return placeIds;
    }

    public void setPlaceIds(List<Integer> placeIds) {
        this.placeIds = placeIds;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
