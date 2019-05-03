package com.challenge.merlin.model;

public class Forecast {

    private boolean umbrella;

    private double precipProbability;

    private String precipType;

    private double latitude;

    private double longitude;


    public Forecast() { }

    public boolean isUmbrella() {
        return umbrella;
    }

    public void setUmbrella(boolean umbrella) {
        this.umbrella = umbrella;
    }

    public double getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public void setPrecipType(String precipType) {
        this.precipType = precipType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
