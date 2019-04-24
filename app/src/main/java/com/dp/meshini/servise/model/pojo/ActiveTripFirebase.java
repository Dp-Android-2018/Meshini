package com.dp.meshini.servise.model.pojo;

import java.io.Serializable;
import java.util.List;

public class ActiveTripFirebase implements Serializable {
    private int next_destination;
    private List<FirebasePlace>Places;
    private int request_id;

    public int getNext_destination() {
        return next_destination;
    }

    public void setNext_destination(int next_destination) {
        this.next_destination = next_destination;
    }

    public List<FirebasePlace> getPlaces() {
        return Places;
    }

    public void setPlaces(List<FirebasePlace> places) {
        Places = places;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }
}
