package com.example.common.model;

import java.io.Serializable;

public class Transfer implements Serializable {

    private String id;

    public Transfer(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
