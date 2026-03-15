package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.services.RservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LocationController {

    @Autowired
    private RservationService service;

    //View all locations
    @GetMapping("/locations")
    public String showLocations(Model model) {
        model.addAttribute("locations", service.getAllLocations());
        return "location-datatable";
    }

    //Show form
    @GetMapping("/locations/add")
    public String showAddLocationForm() {
        return "location_form";
    }

    // Process form
    @PostMapping("/locations/add")
    public String addLocation(@RequestParam int id, @RequestParam String name) {
        service.addLocation(id, name);
        return "redirect:/locations";
    }
}