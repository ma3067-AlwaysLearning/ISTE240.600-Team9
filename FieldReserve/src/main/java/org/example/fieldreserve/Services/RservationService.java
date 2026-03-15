package org.example.fieldreserve.services;

import org.example.fieldreserve.model.Location;

import org.example.fieldreserve.model.User;
import org.example.fieldreserve.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class RservationService {
    private final List<User> users;
    private List<Reservation> reservations = new ArrayList<>();
    public final List<Location> location;


    public RservationService() {
        this.users = new ArrayList<>();

        seedData();
        this.location = new ArrayList<>();
    }

    private void seedData() {
        location.add(new Location("Qusais Fields","Al Qusais","Dubai", 10));
        location.add(new Location("Barsha Courts","Al Barsha","Dubai", 3));
        // Seed Users
        users.add(new User(
                1,
                "Osama Ahmad",
                "osama@example.com",
                "0501234567",
                "Admin",
                "Pass@123",
                "2026-02-20"
        ));

        users.add(new User(
                2,
                "Rashid Ali",
                "rashid@example.com",
                "0509876543",
                "Customer",
                "User@456",
                "2026-02-21"
        ));

        reservations.add(new Reservation(1, LocalDate.of(2022, 3, 20), LocalTime.parse("18:00"), LocalTime.parse("20:00"), 100, "Accepted", true));
        reservations.add(new Reservation(2, LocalDate.of(2026, 3, 21), LocalTime.parse("19:00"), LocalTime.parse("20:00"), 120, "Reserved", false));
    }

    // User Method
    public List<User> getAllUsers() {
        return users;
    }

    public List<Location> getAllLocations() {
        return location;
    }
    public void addUser(User user) {
        users.add(user);
    }

    // Returns an array showing all existing reservations
    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public void addLocation(Location location) {
        this.location.add(location);
    }

    // Adds a new reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

}
