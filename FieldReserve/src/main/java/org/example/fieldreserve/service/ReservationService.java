package org.example.fieldreserve.service;

import jakarta.transaction.Transactional;
import org.example.fieldreserve.model.Reservation;
import org.example.fieldreserve.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this class as a service component
@Transactional // Used to verify that database operations are executed
public class ReservationService {

    // Used to inject the ReservtionRepository dependency automatically
    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation saveReservation(Reservation reservationToSave) {
        // Ensures that the data input is accurately specified and saves the reservation to the database
        if (reservationToSave.getReservationDate() == null)
            throw new IllegalArgumentException("Reservation date cannot be null");

        if (reservationToSave.getStartTime() == null || reservationToSave.getEndTime() == null)
            throw new IllegalArgumentException("Start time and End time are required");

        if (!reservationToSave.getEndTime().isAfter(reservationToSave.getStartTime()))
            throw new IllegalArgumentException("End time must be after start time");

        if (reservationToSave.getUserId() == null)
            throw new IllegalArgumentException("User ID cannot be null");

        if (reservationToSave.getFieldId() == null)
            throw new IllegalArgumentException("Field ID cannot be null");

        return reservationRepository.save(reservationToSave);
    }

    // Returns all existing reservations from the database
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Uses reservation IDs to retrieve them
    public Optional<Reservation> getReservationById(Integer id) {
        return reservationRepository.findById(id);
    }

    // Uses reservation Status (confirmed, pending, cancelled) to retrieve them
    public List<Reservation> getReservationsByStatus(String status) {
        return reservationRepository.findByReservationStatus(status);
    }

    // Deletes reservations by using their IDs
    public void deleteReservationById(Integer id) {
        reservationRepository.deleteById(id);
    }

    // Updates an existing reservation with new values
    public Reservation updateReservation(Integer id, Reservation reservationToUpdate) {

        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        existingReservation.setReservationDate(reservationToUpdate.getReservationDate());
        existingReservation.setStartTime(reservationToUpdate.getStartTime());
        existingReservation.setEndTime(reservationToUpdate.getEndTime());
        existingReservation.setTotalCost(reservationToUpdate.getTotalCost());
        existingReservation.setReservationStatus(reservationToUpdate.getReservationStatus());
        existingReservation.setPaymentStatus(reservationToUpdate.isPaymentStatus());

        existingReservation.setUserId(reservationToUpdate.getUserId());
        existingReservation.setFieldId(reservationToUpdate.getFieldId());

        return reservationRepository.save(existingReservation);
    }
}