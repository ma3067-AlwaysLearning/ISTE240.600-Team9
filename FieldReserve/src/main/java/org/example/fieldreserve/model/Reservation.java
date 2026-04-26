// Muhammad Rashid (UID: 421007820)
// Responsibilities: Creating the Reservation entity, repository, service, controller, and frontend page

package org.example.fieldreserve.model;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

// Allows the Reservation class to be mapped to the reservations table in the MySQL database //
@Entity
@Table(name = "reservations")
public class Reservation {

    // Identifies a unique value (primary key) in the database
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    // Creates the required columns for this database with constraints (nullable = false)

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "total_cost", nullable = false)
    private double totalCost;

    @Column(name = "reservation_status", nullable = false)
    private String reservationStatus;

    @Column(name = "payment_status", nullable = false)
    private boolean paymentStatus;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "field_id", nullable = false)
    private Integer fieldId;

    // Default constructor
    public Reservation() {
    }

    // Parameterized constructor
    public Reservation(int reservationId, LocalDate reservationDate, LocalTime startTime, LocalTime endTime,
                       double totalCost, String reservationStatus, boolean paymentStatus,
                       Integer userId, Integer fieldId) {

        this.reservationId = reservationId;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.reservationStatus = reservationStatus;
        this.paymentStatus = paymentStatus;
        this.userId = userId;
        this.fieldId = fieldId;
    }

    // Getters & Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    // Used to set a certain date for the reservation
    public void selectReservationDate(LocalDate date) {
        this.reservationDate = date;
    }

    // Used to set the start and end times of the booking
    public void selectBookingDuration(LocalTime start, LocalTime end) {
        if (end.isAfter(start)) {
            this.startTime = start;
            this.endTime = end;
        }
    }

    // Used to calculate the total cost
    public void calculateTotalCost(double hourlyRate) {
        if (startTime != null && endTime != null) {
            long duration = Duration.between(startTime, endTime).toHours();
            this.totalCost = duration * hourlyRate;
        }
    }
}