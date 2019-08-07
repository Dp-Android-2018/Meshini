package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PastUpcommingSharedListItem {

    @SerializedName("package_id")
    private int packageId;

    @SerializedName("reservation_id")
    private int reservationId;

    @SerializedName("Company_id")
    private int CompanyId;

    @SerializedName("Company_name")
    private String CompanyName;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("Company_image")
    private String CompanyImage;

    @SerializedName("room_type")
    private String roomType;

    @SerializedName("price_per_person")
    private double pricePerPerson;

    @SerializedName("no_of_persons")
    private int noOfPersons;

    @SerializedName("city_name")
    private List<String> cityName;

    @SerializedName("payment_type")
    private String paymentType;

    @SerializedName("status")
    private String status;

    @SerializedName("total_amounts")
    private double totalAmounts;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int companyId) {
        CompanyId = companyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyImage() {
        return CompanyImage;
    }

    public void setCompanyImage(String companyImage) {
        CompanyImage = companyImage;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(int noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    public List<String> getCityName() {
        return cityName;
    }

    public void setCityName(List<String> cityName) {
        this.cityName = cityName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmounts() {
        return totalAmounts;
    }

    public void setTotalAmounts(double totalAmounts) {
        this.totalAmounts = totalAmounts;
    }
}
