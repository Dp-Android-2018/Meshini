package com.dp.meshini.servise.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Links {
    @SerializedName("first")
    private String first;

    @SerializedName("last")
    private String last;

    @SerializedName("prev")
    private String prev;

    @SerializedName("next")
    private String next;

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getPrev() {
        return prev;
    }

    public String getNext() {
        return next;
    }
}
