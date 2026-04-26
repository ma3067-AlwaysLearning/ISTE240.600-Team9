package org.example.fieldreserve.model;
import jakarta.persistence.*;

import java.util.ArrayList;


@Entity
@Table(name="locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationID; // Unique identifier for a location area

    @Column(nullable = false)
    private String locationName; // A name given for the location

    @Column(nullable = false)
    private String locationArea; // A place inside a city, like Dubai Silicon Oasis

    @Column(nullable = false)
    private String locationCity; // Cities like Dubai,Sharjah and so ...

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private ArrayList<Field> fields = new ArrayList<>(); //Holds all fields that belong in a single location


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
        field.setLocation(this);
    }

    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
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
