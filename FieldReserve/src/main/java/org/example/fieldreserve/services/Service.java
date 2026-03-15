package org.example.fieldreserve.services;

import org.example.fieldreserve.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Service {

    private List<Reservation> reservations = new ArrayList<>();

    // Examples of reservations
    public void seedReservations() {

        reservations.add(new Reservation(1, "2026-03-20", "18:00", "20:00"));
        reservations.add(new Reservation(2, "2026-03-21", "19:00", "20:00"));

    }

    // Returns an array showing all existing reservations
    public List<Reservation> getAllReservations() {
        return reservations;
    }

    // Adds a new reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

}