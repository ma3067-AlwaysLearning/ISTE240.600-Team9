package org.example.fieldreserve.Services;

import org.example.fieldreserve.model.Field;
import org.example.fieldreserve.model.Location;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Service {

    private List<Location> locations = new ArrayList<>();
    private List<Field> fields = new ArrayList<>();

    //Constructor to seed initial data
    public DataService() {
        seedLocations();
        seedFields();
    }

    public void seedLocations() {
        locations.add(new Location(1, "Dubai"));
        locations.add(new Location(2, "Abu Dhabi"));
        locations.add(new Location(3, "Sharjah"));
    }

    public void seedFields() {
        fields.add(new Field(1, "Football Field"));
        fields.add(new Field(2, "Football Field"));
        fields.add(new Field(3, "Football Field"));
    }

    //This returns all the locations in a list
    public List<Location> getAllLocations() {return locations;}
    //Same thing as the previous function but for fields
    public List<Field> getAllFields() {return fields;}


    public void addLocation(int id, String name) {
        Location location = new Location(id, name);
        locations.add(location);
    }

    public void addField(int id, String name) {
        Field field = new Field(id, name);
        fields.add(field);
    }
}