package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.service.ReservationService;
import org.example.fieldreserve.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LocationController {

    @Autowired
    private ReservationService service;

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
    public String addLocation(
            @RequestParam("locationID") int locationID,
            @RequestParam("locationName") String locationName,
            @RequestParam("locationArea") String locationArea,
            @RequestParam("locationCity") String locationCity) {

        Location location = new Location(locationName, locationArea, locationCity, locationID);
        service.addLocation(location);
        return "redirect:/locations";
    }
}