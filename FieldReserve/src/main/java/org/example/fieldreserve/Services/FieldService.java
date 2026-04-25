package org.example.fieldreserve.Services;

import org.example.fieldreserve.model.Field;
import org.example.fieldreserve.repository.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//Student Name: Araz Hafez
//ID: 433006827

@Service
public class FieldService {

    private final FieldRepository fieldRepository;

    //Constructor
    public FieldService(FieldRepository fieldRepository) {this.fieldRepository = fieldRepository;}

    //to get all of our fields --> returns a list of them
    public List<Field> getAllFields() {return fieldRepository.findAll();}

    //to get one field via its ID
    public Field getFieldById(Integer id) {
        return fieldRepository.findById(id).orElse(null);
    }

    //Searching fields by name
    public List<Field> searchFieldsByName(String name) {
        return fieldRepository.findByFieldName(name);
    }

    //to create field
    public Field addField(Field field) {
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
}