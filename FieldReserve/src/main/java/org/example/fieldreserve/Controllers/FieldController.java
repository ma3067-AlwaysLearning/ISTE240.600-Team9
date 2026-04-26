package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FieldController {

    @Autowired
    private DataService dataService;

    //View all fields
    @GetMapping("/fields")
    public String showFields(Model model) {
        model.addAttribute("fields", dataService.getAllFields());
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
        dataService.addField(id, name);
        return "redirect:/fields";
    }
}