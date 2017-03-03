package com.bucalexisproyectoriego.riego;

/**
 * Created by bucalexis on 2/25/17.
 */

public class Record {
    private int id;
    private String name;
    private int crop_id;
    private int stage_id;
    private float result;
    private float cc;
    private float ha;
    private float latitude;
    private float longitude;
    private String lote;
    private String date;

    public Record() {
    }

    public Record(String name, String date, int crop, int stage, float cc, float ha, float result,
        float latitude, float longitude, String lote) {
        this.name = name;
        this.date = date;
        this.crop_id = crop;
        this.stage_id = stage;
        this.cc = cc;
        this.ha = ha;
        this.result = result;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lote = lote;
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

    public int getCrop_id() {
        return crop_id;
    }

    public void setCrop_id(int crop_id) {
        this.crop_id = crop_id;
    }

    public int getStage_id() {
        return stage_id;
    }

    public void setStage_id(int stage_id) {
        this.stage_id = stage_id;
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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getHa() {
        return ha;
    }

    public void setHa(float ha) {
        this.ha = ha;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
