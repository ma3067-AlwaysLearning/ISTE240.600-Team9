package org.example.fieldreserve.services;


import org.example.fieldreserve.model.User;
import org.example.fieldreserve.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    private final List<User> users;
    private List<Reservation> reservations = new ArrayList<>();

    public Service() {
        this.users = new ArrayList<>();

        seedData();
    }

    private void seedData() {
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
        
        reservations.add(new Reservation(1, "2026-03-20", "18:00", "20:00"));
        reservations.add(new Reservation(2, "2026-03-21", "19:00", "20:00"));
    }

    // User Method
    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
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
