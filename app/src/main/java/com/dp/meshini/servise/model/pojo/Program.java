package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Program {

    @SerializedName("hotel_name")
    private String hotelName;

    @SerializedName("available_rooms")
    private List<String> availableRooms;

    @SerializedName("meals")
    private String meals;

    @SerializedName("transportations")
    private String transportations;

    @SerializedName("city_name")
    private List<String> cityName;


    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<String> getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(List<String> availableRooms) {
        this.availableRooms = availableRooms;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getTransportations() {
        return transportations;
    }

    public void setTransportations(String transportations) {
        this.transportations = transportations;
    }

    public List<String> getCityName() {
        return cityName;
    }

    public void setCityName(List<String> cityName) {
        this.cityName = cityName;
    }
}
