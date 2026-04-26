package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.model.User;
import org.example.fieldreserve.model.Location;
import org.example.fieldreserve.model.Field;
import org.example.fieldreserve.model.Reservation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class FormController {

    private final org.example.fieldreserve.service.ReservationService reservationService;


    public FormController(org.example.fieldreserve.service.ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // User form
    @PostMapping("/user")     // @PostMapping handles POST requests from the defined pages
    public String addUser(
            @RequestParam("userID") int userID, @RequestParam("fullName") String fullName, @RequestParam("phone") String phone,
            @RequestParam("role") String role, @RequestParam("password") String password, @RequestParam("date") String date) {

        // Used to create a new object using the input values from the form pages
        User user = new User(userID, fullName, phone, role, password, LocalDate.parse(date));
        reservationService.addUser(user); // Adds the object to the service
        return "redirect:/success/User"; // Brings users to the success page through path variables
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
        reservationService.addLocation(location);
        reservationService.addField(field);
        return "redirect:/success/Field+Location";
    }

    // Reservation form
    @PostMapping("/reservation")
    public String addReservation(
            @RequestParam("reservationID") int reservationID, @RequestParam("reservationDate") String reservationDate,
            @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {

        Reservation reservation = new Reservation(reservationID, LocalDate.parse(reservationDate),
                LocalTime.parse(startTime), LocalTime.parse(endTime));
        reservationService.addReservation(reservation);
        return "redirect:/success/Reservation";
    }
}