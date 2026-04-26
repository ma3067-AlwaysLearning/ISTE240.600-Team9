package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.model.Field;
import org.example.fieldreserve.Services.FieldService;
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
    public Field getFieldById(@PathVariable Integer id) {
        return fieldService.getFieldById(id);
    }

    //for searching fields by name
    @GetMapping("/search")
    public List<Field> searchFields(@RequestParam String name) {
        return fieldService.searchFieldsByName(name);
    }

    //to add new field --> use POST here since we are giving server data
    @PostMapping
    public Field addField(@RequestBody Field field) {
        return fieldService.addField(field);
    }

    //to update a field
    @PutMapping("/{id}")
    public Field updateField(@PathVariable Integer id, @RequestBody Field field) {
        return fieldService.updateField(id, field);
    }

    //used to delete a field
    @DeleteMapping("/{id}")
    public String deleteField(@PathVariable Integer id) {
        boolean deleted = fieldService.deleteField(id);

        if (deleted == true) {
            return "Field deleted successfully";
        } else {
            return "Field not found";
        }
    }
}