package org.example.fieldreserve.repository;

// Student: Osama Ahmad
// ID: 764000269

import org.example.fieldreserve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByEmailContainingIgnoreCase(String email);

    List<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = ?1")
    List<User> findUsersByRole(String role);

    @Modifying
    @Query("UPDATE User u SET u.phone = ?2 WHERE u.userId = ?1")
    int updatePhoneById(Integer id, String phone);
}