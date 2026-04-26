package org.example.fieldreserve.service;

// Student: Osama Ahmad
// ID: 764000269

import org.example.fieldreserve.entity.User;
import org.example.fieldreserve.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> searchUsersByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }

    public List<User> searchUsersByRole(String role) {
        return userRepository.findUsersByRole(role);
    }

    public User addUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User newUserData) {
        User oldUser = getUserById(id);

        if (oldUser == null) {
            return null;
        }

        oldUser.setFullName(newUserData.getFullName());
        oldUser.setEmail(newUserData.getEmail());
        oldUser.setPhone(newUserData.getPhone());
        oldUser.setRole(newUserData.getRole());
        oldUser.setPassword(newUserData.getPassword());

        return userRepository.save(oldUser);
    }

    public boolean deleteUser(Integer id) {
        User user = getUserById(id);

        if (user == null) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }

    @Transactional
    public int updatePhoneById(Integer id, String phone) {
        return userRepository.updatePhoneById(id, phone);
    }
}