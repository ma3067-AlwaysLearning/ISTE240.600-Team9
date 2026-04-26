package org.example.fieldreserve.repository;

import org.example.fieldreserve.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// Used to extend the JpaRepository for the Reservation entity
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    // Used to return all reservations
    List<Reservation> findAll();

    // Used to return reservation based on their status (pending, confirmed, deleted)
    List<Reservation> findByReservationStatus(String reservationStatus);

    // Used to return reservations by their ID
    Optional<Reservation> findById(Integer id);

    // Gets a reservation by its ID using @Query
    @Query("SELECT r FROM Reservation r WHERE r.reservationId = :id")
    Optional<Reservation> findReservationById(@Param("id") Integer id);

    // Used to retrieve reservations based on their date
    @Query("SELECT r FROM Reservation r WHERE r.reservationDate = :date")
    List<Reservation> findReservationsByDate(@Param("date") LocalDate date);

    // Retrieves reservations made by a certain customer using their IDs
    List<Reservation> findByUserId(Integer userId);

    // Used to retrieve reservations for a particular field by its ID
    List<Reservation> findByFieldId(Integer fieldId);

    // Used to update reservation status by using its ID as a filter
    @Modifying // Required so that data changes
    @Query("UPDATE Reservation r SET r.reservationStatus = :status WHERE r.reservationId = :id")
    void updateReservationStatusById(@Param("id") Integer id,
                                     @Param("status") String status);

    // Used to delete a reservation by using its ID
    void deleteById(Integer id);

    // Used to check whether or not a reservation exists by using its ID
    boolean existsById(Integer id);
}