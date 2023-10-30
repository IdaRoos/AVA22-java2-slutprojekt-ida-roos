package org.example.model;

public class Item {

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

