package com.teamoutis.realestateapp.Models;

/**
 * Created by Muhammad on 4/7/2017.
 */

public class Property {

    private String address;
    private int bedroom;
    private int bathroom;
    private int zipcode;
    private double price;
    private String link;

    public Property(String address, int bedroom, int bathroom, int zipcode,
                    double price, String link){
        this.address = address;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.zipcode = zipcode;
        this.price = price;
        this.link = link;
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

    public void setZipCode(int zipcode) {
        this.zipcode = zipcode;
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

    public int getZipCode() {
        return zipcode;
    }

    public double getPrice() {
        return price;
    }

    public String getLink() { return link; }
}
