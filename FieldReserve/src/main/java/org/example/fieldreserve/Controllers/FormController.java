package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.model.User;
import org.example.fieldreserve.model.Location;
import org.example.fieldreserve.model.Field;
import org.example.fieldreserve.model.Reservation;
import org.example.fieldreserve.services.RservationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class FormController {

    private final RservationService service;


    public FormController(RservationService service) {
        this.service = service;
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
            @RequestParam("locationID") int locationID, @RequestParam("locationArea") String locationArea,
            @RequestParam("locationName") String locationName, @RequestParam("locationCity") String locationCity,
            @RequestParam("fieldID") int fieldID, @RequestParam("fieldName") String fieldName,
            @RequestParam("fieldLocation") String fieldLocation) {

        Location location = new Location(locationID, locationArea, locationName, locationCity);
        Field field = new Field(fieldID, fieldName, fieldLocation);
        service.addLocation(location);
        service.addField(field);
        return "redirect:/success/Field+Location";
    }

    // Reservation form
    @PostMapping("/reservation")
    public String addReservation(
            @RequestParam("reservationID") int reservationID, @RequestParam("reservationDate") String reservationDate,
            @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {

        Reservation reservation = new Reservation(reservationID, LocalDate.parse(reservationDate),
                LocalTime.parse(startTime), LocalTime.parse(endTime));
        service.addReservation(reservation);
        return "redirect:/success/Reservation";
    }
}