package org.example.fieldreserve.model;

import org.springframework.stereotype.Component;

@Component
public class Field {

    private int fieldID; //Every Field will be identified with a unique ID
    private String fieldName; //Every Field has a name, whether it is in a school, stadium etc
    private String fieldLocation; //The location of the specific field
    private double hourlyRate; //Hourly rate; varies for different fields
    private boolean availabilityStatus; //This shows whether the field is still available or not

    //Default-Non arg constructor
    public Field() {}

    //Parameterized Constructor
    public Field(int fieldID, String fieldName, String fieldLocation, double hourlyRate, boolean availabilityStatus){
        this.fieldID = fieldID;
        this.fieldName=fieldName;
        this.fieldLocation=fieldLocation;
        this.hourlyRate=hourlyRate;
        this.availabilityStatus=availabilityStatus;
    }

    //Getters and Setters
    public int getFieldID() {return fieldID;}
    public void setFieldID(int fieldID) {this.fieldID = fieldID;}

    public String getFieldName() {return fieldName;}
    public void setFieldName(String fieldName) {this.fieldName = fieldName;}

    public String getFieldLocation() {return fieldLocation;}
    public void setFieldLocation(String fieldLocation) {this.fieldLocation = fieldLocation;}

    public double getHourlyRate() {return hourlyRate;}
    public void setHourlyRate(double hourlyRate) {this.hourlyRate = hourlyRate;}

    public boolean isAvailabilityStatus() {return availabilityStatus;}
    public void setAvailabilityStatus(boolean availabilityStatus) {this.availabilityStatus = availabilityStatus;}

    //Additional Methods

    //Function to check if field is available
    public boolean isAvailable() {
        return availabilityStatus;
    }

    //Function to book a field
    public void reserveField() {
        if (availabilityStatus) {
            availabilityStatus = false;
        }
    }

    //Function to free the field (After a booking is done or cancelled)
    public void releaseField() {
        availabilityStatus = true;
    }

    //Function to calculate cost for X hours
    public double calculateCost(int hours) {
        return hourlyRate * hours;
    }

    //Function to display field info
    public String getFieldInfo() {
        return "Field: " + fieldName +
                ", Location: " + fieldLocation +
                ", Hourly Rate: AED" + hourlyRate +
                ", Available: " + availabilityStatus;
    }

    //Additional: toString method
    @Override
    public String toString() {
        return "Field: " + fieldName +
                ", Location: " + fieldLocation +
                ", Hourly Rate: AED" + hourlyRate +
                ", Available: " + availabilityStatus;
    }
}
