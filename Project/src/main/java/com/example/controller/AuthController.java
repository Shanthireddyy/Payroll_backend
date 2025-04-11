package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User savedUser = userService.register(user);  // Register and get saved user
        return ResponseEntity.ok(savedUser);          // ✅ Send back full user with ID
    }

    // Admin Login
    @PostMapping("/admin-login")
    public ResponseEntity<String> adminLogin(@RequestBody User user) {
        boolean success = userService.loginAdmin(user.getEmail(), user.getPassword(), "ADMIN");
        if (success) {
            return ResponseEntity.ok("Admin login successful!");
        } else {
            return ResponseEntity.status(401).body("Invalid admin credentials.");
        }
    }

    
    @PostMapping("/employee-login")
    public ResponseEntity<?> employeeLogin(@RequestBody User user) {
        Optional<User> foundUser = userService.findByEmailAndRole(user.getEmail(), "EMPLOYEE");

        if (foundUser != null && foundUser.isPresent()) {
            User emp = foundUser.get();

            // Optional: add password check with encoder
            if (!emp.getPassword().equals(user.getPassword())) {
                return ResponseEntity.status(401).body("Incorrect password.");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("id", emp.getId());
            response.put("name", emp.getName());
            response.put("email", emp.getEmail());
            response.put("role", emp.getRole());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid employee credentials.");
        }
    }


    // Reset Password
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        boolean success = userService.resetPassword(email, newPassword);
        return success ? "Password reset successful!" : "User not found.";
    }
}
