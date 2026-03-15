package org.example.fieldreserve.model;

import org.springframework.stereotype.Component;


public class User {
    private int  ID;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private String password;
    private String createdAt;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(int ID, String fullName, String email, String phone,
                String role, String password, String createdAt) {
        this.ID = ID;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.password = password;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public int getID() {return ID;}

    public void setID(int ID) {this.ID = ID;}

    public String getFullName() {return fullName;}

    public void setFullName(String fullName) {this.fullName = fullName;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getCreatedAt() {return createdAt;}

    public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}

    //make sure profile is complete
    public boolean isProfileComplete() {
        return fullName != null && !fullName.isBlank() //used isBlank() because it doesn't allow whitespaces as result
                && email != null && !email.isBlank()
                && phone != null && !phone.isBlank();
    }

    // Email validation method
    public boolean hasValidEmailFormat() {
        return email != null && email.contains("@") && email.contains(".");
    }

    // Phone validation method
    public boolean hasValidPhoneFormat() {
        return phone != null && phone.length() == 10; // simple check
    }

    // Password validation method
    public boolean isPasswordStrong() {
        return password != null
                && password.length() > 6
                && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).+$");
    }

    //toString method
    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
