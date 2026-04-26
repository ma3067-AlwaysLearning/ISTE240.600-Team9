package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.model.Field;
import org.example.fieldreserve.service.FieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Student Name: Araz Hafez
//ID: 433006827


//Modified the controller from last phase into a REST controller:
@RestController
@RequestMapping("/api/fields")
public class FieldRestController {

    private final FieldService fieldService;

    //constructor
    public FieldRestController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    //To get all fields
    @GetMapping
    public List<Field> getAllFields() {
        return fieldService.getAllFields();
    }

    //To get one field using the ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getFieldById(@PathVariable Integer id) {
        Field field = fieldService.getFieldById(id);

        if (field == null) {
            return new ResponseEntity<>("Field not found.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(field, HttpStatus.OK);
    }

    //for searching fields by name
    @GetMapping("/search")
    public List<Field> searchFields(@RequestParam String name) {
        return fieldService.searchFieldsByName(name);
    }

    //to add new field --> use POST here since we are giving server data
    @PostMapping
    public ResponseEntity<?> addField(@RequestBody Field field) {
        try {
            Field savedField = fieldService.addField(field);
            return new ResponseEntity<>(savedField, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding field.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //to update a field
    @PutMapping("/{id}")
    public ResponseEntity<?> updateField(@PathVariable Integer id, @RequestBody Field field) {
        try {
            Field updatedField = fieldService.updateField(id, field);

            if (updatedField == null) {
                return new ResponseEntity<>("Field not found.", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(updatedField, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating field.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //used to delete a field
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteField(@PathVariable Integer id) {
        boolean deleted = fieldService.deleteField(id);

        if (deleted) {
            return new ResponseEntity<>("Field deleted successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Field not found.", HttpStatus.NOT_FOUND);
        }
    }
}