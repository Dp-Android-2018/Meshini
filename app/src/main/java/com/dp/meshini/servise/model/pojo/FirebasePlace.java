package com.dp.meshini.servise.model.pojo;

import java.io.Serializable;

public class FirebasePlace implements Serializable {
    private boolean done;
    private int id;


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
}
