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

}

