package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.Services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FieldController {

    @Autowired
    private ReservationService service;

    //View all fields
    @GetMapping("/fields")
    public String showFields(Model model) {
        model.addAttribute("fields", service.getAllFields());
        return "field-datatable";
    }

    //show form
    @GetMapping("/fields/add")
    public String showAddFieldForm() {
        return "field_form";
    }

    //Process form
    @PostMapping("/fields/add")
    public String addField(@RequestParam int id, @RequestParam String name) {
        service.addField(id, name);
        return "redirect:/fields";
    }
}