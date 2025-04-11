package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register new user
    public User register(User user) {
        return userRepository.save(user);
    }

    // Admin login
    public boolean loginAdmin(String email, String password, String role) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.getRole().equals(role)) {
            return true;
        }
        return false;
    }

    // Employee login (same login method as Admin, but checks for 'EMPLOYEE' role)
    public boolean loginEmployee(String email, String password,String role) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password) && user.getRole().equals(role)) {
            return true;
        }
        return false;
    }

    // Reset Password
    public boolean resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

	public Optional<User> findByEmailAndRole(String email, String role) {
		// TODO Auto-generated method stub
		 return userRepository.findByEmailAndRole(email, role);
	}
}
