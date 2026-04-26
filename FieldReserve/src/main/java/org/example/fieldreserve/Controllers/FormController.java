package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.service.ReservationService;
import org.example.fieldreserve.model.User;
import org.example.fieldreserve.model.Location;
import org.example.fieldreserve.model.Field;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {

    private final ReservationService service;


    public FormController(ReservationService service) {
        this.service = service;
    }

    // Show Location + Field form
    @GetMapping("/locationField")
    public String showLocationFieldForm() {
        return "location-field_form";
    }

    // Osama part
    @PostMapping("/user/add")
    public String addUser(
            @RequestParam("userID") int userID,
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("role") String role,
            @RequestParam("password") String password,
            @RequestParam("createdAt") String createdAt) {

        User user = new User(userID, fullName, email, phone, role, password, createdAt);
        service.addUser(user);
        return "redirect:/user";
    }

    // Location + Field form
    @PostMapping("/locationField")
    public String addLocationAndField(
            @RequestParam("locationID") int locationID,
            @RequestParam("locationArea") String locationArea,
            @RequestParam("locationName") String locationName,
            @RequestParam("locationCity") String locationCity,
            @RequestParam("fieldID") int fieldID,
            @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldLocation") String fieldLocation) {

        Location location = new Location(locationName, locationArea, locationCity, locationID);
        Field field = new Field(fieldID, fieldName, fieldLocation, 0.0, true);

        service.addLocation(location);
        service.addField(field);

        return "redirect:/success/Field+Location";
    }
}