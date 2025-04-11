package com.example.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Notification;
import com.example.repository.NotificationRepository;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepo;

    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody Notification notification) {
        notification.setTimestamp(LocalDateTime.now());
        notificationRepo.save(notification);
        return ResponseEntity.ok("Notification sent!");
    }

    @GetMapping("/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationRepo.findByUserIdOrderByTimestampDesc(userId);
    }

    @PutMapping("/mark-read/{id}")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        Notification n = notificationRepo.findById(id).orElseThrow();
        n.setRead(true);
        notificationRepo.save(n);
        return ResponseEntity.ok("Notification marked as read");
    }
}

