package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.Services.ReservationService;
import org.example.fieldreserve.model.Reservation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    // Displaying the reservation data table
    @GetMapping
    public String showReservations(Model model) {
        model.addAttribute("reservations", service.getAllReservations());
        return "reservation-datatable";
    }

    // Shows the reservation form that needs to be filled
    @GetMapping("/add")
    public String showAddReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation_form";
    }

    // Used to handle users' form submissions
    @PostMapping("/add")
    public String addReservation(@ModelAttribute Reservation reservation) {
        service.addReservation(reservation);
        return "redirect:/reservation";
    }
}