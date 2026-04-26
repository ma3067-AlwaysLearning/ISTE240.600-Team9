package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.model.Reservation;
import org.example.fieldreserve.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marks this class as a RestController class
@RequestMapping("/api/reservations") // Used to request data from /api/reservations
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Creates a new reservation and saves it in the database
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation saved = reservationService.saveReservation(reservation);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Uses the array <Reservation> to display a list of all existing reservations
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {

        List<Reservation> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}") // Gets a reservation by its ID
    public ResponseEntity<Reservation> getReservation(@PathVariable Integer id) {

        Optional<Reservation> reservation = reservationService.getReservationById(id);

        if (reservation.isPresent())
            return new ResponseEntity<>(reservation.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    // Gets reservations based on whether they are confirmed, pending or cancelled
    @GetMapping("/search")
    public ResponseEntity<List<Reservation>> searchByStatus(@RequestParam String status) {

        List<Reservation> reservations = reservationService.getReservationsByStatus(status);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // Used to update an existing reservation using its ID
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Integer id,
                                                         @RequestBody Reservation reservation) {
        try {
            Reservation updated = reservationService.updateReservation(id, reservation);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Used for deleting reservations by their ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Integer id) {
        try {
            reservationService.deleteReservationById(id);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error deleting reservation", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}