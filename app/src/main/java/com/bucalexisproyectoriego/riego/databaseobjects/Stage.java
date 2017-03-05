package com.bucalexisproyectoriego.riego.databaseobjects;

/**
 * Created by bucalexis on 3/2/17.
 */

public class Stage {

    private int id;
    private String name;
    private int crop_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCrop_id() {
        return crop_id;
    }

    public void setCrop_id(int crop_id) {
        this.crop_id = crop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
