package com.bucalexisproyectoriego.riego;

/**
 * Created by bucalexis on 2/25/17.
 */

public class Record {
    private int id;
    private String name;
    private int crop;
    private int stage;
    private float result;
    private float cc;
    private float ha;
    private String date;

    public Record() {
    }

    public Record(String name, String date, int crop, int stage, float cc, float ha, float result) {
        this.name = name;
        this.date = date;
        this.crop = crop;
        this.stage = stage;
        this.cc = cc;
        this.ha = ha;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCrop() {
        return crop;
    }

    public void setCrop(int crop) {
        this.crop = crop;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public float getCc() {
        return cc;
    }

    public void setCc(float cc) {
        this.cc = cc;
    }

    public float getHa() {
        return ha;
    }

    public void setHa(float ha) {
        this.ha = ha;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
