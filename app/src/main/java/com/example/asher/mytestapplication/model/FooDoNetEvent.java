package com.example.asher.mytestapplication.model;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Asher on 20-Jun-15.
 */
public class FooDoNetEvent {
    private int icon;
    private String title;
    private String description;
    private LatLng position;
    private Date startDate;
    private Date endDate;
    private String address;
    private int pickupMethod;

    public static final int PICKUP_METHOD_FREE_PICKUP = 0;
    public static final int PICKUP_METHOD_CONTACT_PUBLISHER = 1;

    public FooDoNetEvent(int icon, String title, String description,
                         LatLng position, Date startDate, Date endDate,
                         String address, int pickupMethod) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.pickupMethod = pickupMethod;
    }

    public int getIcon(){
        return icon;
    }
    public void setIcon(int iconID){
        icon = iconID;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public LatLng getPosition(){
        return position;
    }
    public void setPosition(LatLng position){
        this.position = position;
    }
    public void setPosition(double latitude, double longtitude){
        this.position = new LatLng(latitude, longtitude);
    }

    public Date getStartDate(){
        return startDate;
    }
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }

    public Date getEndDate(){
        return endDate;
    }
    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public int getPickupMethod(){
        return pickupMethod;
    }
    public void setPickupMethod(int pickupMethod){
        switch (pickupMethod){
            case 0:
                this.pickupMethod = PICKUP_METHOD_FREE_PICKUP;
                break;
            default:
                this.pickupMethod = PICKUP_METHOD_CONTACT_PUBLISHER;
        }
    }
}
