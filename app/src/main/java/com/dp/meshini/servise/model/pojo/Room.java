package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Room implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("room_type")
    private String roomType;

    @SerializedName("price")
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
