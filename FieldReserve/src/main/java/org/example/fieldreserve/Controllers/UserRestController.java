package org.example.fieldreserve.Controllers;

// Student: Osama Ahmad
// ID: 764000269

import org.example.fieldreserve.entity.User;
import org.example.fieldreserve.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get one user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // Search users by email
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String email) {
        return userService.searchUsersByEmail(email);
    }

    // Add new user
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // Update user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        boolean deleted = userService.deleteUser(id);

        if (deleted) {
            return "User deleted successfully";
        } else {
            return "User not found";
        }
    }
}