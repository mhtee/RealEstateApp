package com.teamoutis.realestateapp.Models;

/**
 * Created by Muhammad on 4/7/2017.
 */

public class Property {

    private String address;
    private int bedroom;
    private int bathroom;
    private double distance;
    private double price;

    public Property(String address, int bedroom, int bathroom, double distance, double price){
        this.address = address;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.distance = distance;
        this.price = price;
    }

    public void setAddress(String addr) {
        address = addr;
    }

    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }

    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public int getBedroom() {
        return bedroom;
    }

    public int getBathroom() {
        return bathroom;
    }

    public double getDistance() {
        return distance;
    }

    public double getPrice() {
        return price;
    }
}
