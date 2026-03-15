package org.example.fieldreserve.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Location {
    private int locationID; // Unique identifier for a location area
    private ArrayList<Field> fields = new ArrayList<>(); //Holds all fields that belong in a single location
    private String locationName; // A name given for the location
    private String locationArea; // A place inside a city, like Dubai Silicon Oasis
    private String locationCity; // Cities like Dubai,Sharjah and so ...

    //Empty Constructor, unable have only the below constructor alone without some error about parameters not being able linked by autowire?? why is String type trying to be autowired?
    public Location(){

    }

    public Location(String locationName, String locationArea, String locationCity, int locationID) {
        this.locationID = locationID;
        this.locationName = locationName;
        this.locationArea = locationArea;
        this.locationCity = locationCity;
        this.fields = new ArrayList<>();
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }


    public String getLocationArea() {
        return locationArea;
    }

    public void setLocationArea(String locationArea) {
        this.locationArea = locationArea;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public void addField(Field field){
        this.fields.add(field);
        field.setFieldLocation(this.locationArea);
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationID=" + locationID +
                ", fields=" + fields +
                ", locationName='" + locationName + '\'' +
                ", locationArea='" + locationArea + '\'' +
                ", locationCity='" + locationCity + '\'' +
                '}';
    }
}
