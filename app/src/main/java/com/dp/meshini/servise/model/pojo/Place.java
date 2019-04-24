package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("lat")
    private double lat;

    @SerializedName("long")
    private double lang;

    private boolean done;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }
}
