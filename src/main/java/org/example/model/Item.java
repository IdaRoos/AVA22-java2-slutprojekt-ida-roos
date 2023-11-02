package org.example.model;

import java.io.Serializable;

public class Item implements Serializable {

    String id = "";

    public Item(String id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return id;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}

