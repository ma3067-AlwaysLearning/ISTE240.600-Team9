package org.example.fieldreserve.services;


import org.example.fieldreserve.model.User;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    private final List<User> users;

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
    }

    // User Method
    public List<User> getAllUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
