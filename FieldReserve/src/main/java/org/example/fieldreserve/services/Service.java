package org.example.fieldreserve.services;

import org.example.fieldreserve.model.Field;
import org.example.fieldreserve.model.Location;
import org.example.fieldreserve.model.Reservation;
import org.example.fieldreserve.model.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    private final List<User> users;
    private final List<Location> locations;
    private final List<Field> fields;
    private final List<Reservation> reservations;

    public Service() {
        this.users = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.reservations = new ArrayList<>();

        seedData();
    }

    private void seedData() {
        // Seed Locations
        Location location1 = new Location("Silicon Oasis Sports Complex", "Dubai Silicon Oasis", "Dubai", 101);
        Location location2 = new Location("Al Nahda Play Arena", "Al Nahda", "Dubai", 102);

        // Seed Fields
        Field field1 = new Field(201, "Football Field A", "Dubai Silicon Oasis", 250.0, true);
        Field field2 = new Field(202, "Basketball Court B", "Al Nahda", 180.0, true);

        // Link fields to locations
        location1.addField(field1);
        location2.addField(field2);

        locations.add(location1);
        locations.add(location2);

        fields.add(field1);
        fields.add(field2);

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

        // Seed Reservations
        reservations.add(new Reservation(
                301,
                LocalDate.of(2026, 3, 15),
                LocalTime.of(18, 0),
                LocalTime.of(20, 0),
                500.0,
                "CONFIRMED",
                true
        ));

        reservations.add(new Reservation(
                302,
                LocalDate.of(2026, 3, 16),
                LocalTime.of(19, 0),
                LocalTime.of(21, 0),
                360.0,
                "PENDING",
                false
        ));
    }

    // User Method
    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    // Location Method
    public List<Location> getAllLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    // Field Method
    public List<Field> getAllFields() {
        return fields;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    // Reservation Method
    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

}
