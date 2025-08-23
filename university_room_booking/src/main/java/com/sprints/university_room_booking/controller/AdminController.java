package com.sprints.university_room_booking.controller;

import com.sprints.university_room_booking.security.SecurityUtils;
import com.sprints.university_room_booking.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;

    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getAdminProfile() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Admin access successful");
        response.put("adminId", currentUserId);
        response.put("roles", SecurityUtils.getCurrentUserRoles());

        return ResponseEntity.ok(response);
    }
}
