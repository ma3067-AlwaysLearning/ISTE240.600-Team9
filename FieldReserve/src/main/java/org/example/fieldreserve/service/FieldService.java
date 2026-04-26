package org.example.fieldreserve.service;

import org.example.fieldreserve.model.Field;
import org.example.fieldreserve.model.Location;
import org.example.fieldreserve.dataLayer.LocationRepository;
import org.example.fieldreserve.repository.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//Student Name: Araz Hafez
//ID: 433006827

@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final LocationRepository locationRepository;

    //Constructor
    public FieldService(FieldRepository fieldRepository, LocationRepository locationRepository) {
        this.fieldRepository = fieldRepository;
        this.locationRepository = locationRepository;
    }

    //to get all of our fields --> returns a list of them
    public List<Field> getAllFields() {return fieldRepository.findAll();}

    //to get one field via its ID
    public Field getFieldById(Integer id) {
        return fieldRepository.findById(id).orElse(null);
    }

    //Searching fields by name
    public List<Field> searchFieldsByName(String name) {
        return fieldRepository.findByFieldNameContainingIgnoreCase(name);
    }

    //to create field
    public Field addField(Field field) {
        validateField(field);
        return fieldRepository.save(field);
    }

    //to update field
    public Field updateField(Integer id, Field updatedField) {
        return fieldRepository.findById(id).map(field -> {
            field.setFieldName(updatedField.getFieldName());
            field.setFieldLocation(updatedField.getFieldLocation());
            field.setHourlyRate(updatedField.getHourlyRate());
            field.setAvailabilityStatus(updatedField.getAvailabilityStatus());
            field.setFieldType(updatedField.getFieldType());
            field.setCapacity(updatedField.getCapacity());
            validateField(updatedField);
            return fieldRepository.save(field);
        }).orElse(null);
    }

    //to delete field
    public boolean deleteField(Integer id) {
        if (fieldRepository.existsById(id)) {
            fieldRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validateField(Field field) {
        if (field.getFieldName() == null || field.getFieldName().trim().isEmpty()) {
            throw new IllegalArgumentException("Field name is required.");
        }

        if (field.getFieldLocation() == null || field.getFieldLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Please choose a registered location.");
        }

        if (field.getHourlyRate() <= 0) {
            throw new IllegalArgumentException("Hourly rate must be greater than 0.");
        }

        if (field.getAvailabilityStatus() == null || field.getAvailabilityStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Availability status is required.");
        }

        if (!field.getAvailabilityStatus().equals("AVAILABLE") &&
                !field.getAvailabilityStatus().equals("BOOKED")) {
            throw new IllegalArgumentException("Availability status must be AVAILABLE or BOOKED.");
        }

        if (!isRegisteredLocation(field.getFieldLocation())) {
            throw new IllegalArgumentException("Invalid location. Please choose one of the registered locations.");
        }
    }

    private boolean isRegisteredLocation(String selectedLocation) {
        List<Location> locations = locationRepository.findAll();

        for (Location location : locations) {
            if (formatLocation(location).equals(selectedLocation)) {
                return true;
            }
        }

        return false;
    }

    private String formatLocation(Location location) {
        return location.getLocationName() + " - " +
                location.getLocationArea() + ", " +
                location.getLocationCity();
    }
}