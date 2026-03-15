package org.example.fieldreserve.model;

import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


@Component
public class Reservation {

    private int reservationID;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private double totalCost;
    private String reservationStatus;
    private boolean paymentStatus;

    // Default constructor
    public Reservation() {
    }

    // Parameterized constructor
    public Reservation(int reservationID, LocalDate reservationDate, LocalTime startTime, LocalTime endTime,
                       double totalCost, String reservationStatus, boolean paymentStatus) {
        this.reservationID = reservationID;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.reservationStatus = reservationStatus;
        this.paymentStatus = paymentStatus;
    }

    // Getters & Setters
    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
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
        this.paymentStatus = paymentStatus;     // Used to display the paymentStatus
    }

    public void showAvailableBookings() {
        System.out.println("Available booking hours: ");    // This will print out a list of all booking hours still available
    }

    public void selectReservationDate(LocalDate date) {
        this.reservationDate = date;    // Allows the user to select a certain available date for the reservation
        System.out.println("You have selected: " + reservationDate);    // Used to print the reservation date selected by the user
    }

    // Allows the user to select the booking duration and uses if-else statements to check if the selected duration is valid or not
    public void selectBookingDuration(LocalTime start, LocalTime end) {
        if (end.isAfter(start)) {
            this.startTime = start;
            this.endTime = end;
            System.out.println("The time slot you have booked is from: " + startTime + " to " + endTime);
        }
        else {
            System.out.println("Error! This booking is invalid!");
        }
    }

    // Used to calculate the total cost of the reservation
    public void calculateTotalCost(double hourlyRate) {
        long duration = Duration.between(startTime, endTime).toHours();
        this.totalCost = duration * hourlyRate;
        System.out.println("Total cost calculated: " + totalCost);
    }

    // The if statements are used to process the reservation by using specific conditions
    public void processReservation() {
        if (reservationDate != null && startTime != null && endTime != null) {
            this.reservationStatus = "CONFIRMED";
            System.out.println("Your reservation has been confirmed!");
        } else {
            System.out.println("You have not yet finished adding all your preferences for the reservation.");
        }
    }

    // The if statements are used to process the payment by using specific conditions
    public void processPayment() {
        if (totalCost > 0 && reservationStatus.equals("CONFIRMED")) {
            this.paymentStatus = true;
            System.out.println("Your payment has been processed sucessfully. Thank you very much!");
        }
        else {
            System.out.println("There was an error in processing your payment. Please try again.");
        }
    }

}
