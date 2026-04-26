package org.example.fieldreserve.service;

import jakarta.transaction.Transactional;
import org.example.fieldreserve.model.Reservation;
import org.example.fieldreserve.model.Field;
import org.example.fieldreserve.repository.ReservationRepository;
import org.example.fieldreserve.repository.FieldRepository;
import org.example.fieldreserve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service // Marks this class as a service component
@Transactional // Used to verify that database operations are executed
public class ReservationService {

    // Used to inject the ReservtionRepository, userRepository, and fieldRepository  dependency automatically
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FieldRepository fieldRepository;

    public Reservation saveReservation(Reservation reservationToSave) {
        validateAndPrepareReservation(reservationToSave);
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

        validateAndPrepareReservation(reservationToUpdate);
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

    private void validateAndPrepareReservation(Reservation reservation) {
        if (reservation.getUserId() == null) {
            throw new IllegalArgumentException("Please choose a registered user.");
        }

        if (!userRepository.existsById(reservation.getUserId())) {
            throw new IllegalArgumentException("Selected user does not exist.");
        }

        if (reservation.getFieldId() == null) {
            throw new IllegalArgumentException("Please choose a registered field.");
        }

        Field field = fieldRepository.findById(reservation.getFieldId())
                .orElseThrow(() -> new IllegalArgumentException("Selected field does not exist."));

        if (reservation.getReservationDate() == null) {
            throw new IllegalArgumentException("Reservation date is required.");
        }

        if (reservation.getStartTime() == null || reservation.getEndTime() == null) {
            throw new IllegalArgumentException("Start time and end time are required.");
        }

        if (!reservation.getEndTime().isAfter(reservation.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        if (reservation.getReservationStatus() == null ||
                reservation.getReservationStatus().trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation status is required.");
        }

        String status = reservation.getReservationStatus().trim().toUpperCase();

        if (!status.equals("PENDING") &&
                !status.equals("CONFIRMED") &&
                !status.equals("CANCELLED")) {
            throw new IllegalArgumentException("Reservation status must be PENDING, CONFIRMED, or CANCELLED.");
        }

        reservation.setReservationStatus(status);

        long minutes = Duration.between(reservation.getStartTime(), reservation.getEndTime()).toMinutes();
        double totalCost = (minutes / 60.0) * field.getHourlyRate();

        reservation.setTotalCost(totalCost);
    }
}