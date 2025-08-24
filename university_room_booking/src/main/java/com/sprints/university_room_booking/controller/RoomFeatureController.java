package com.sprints.university_room_booking.controller;

import com.sprints.university_room_booking.dto.*;
import com.sprints.university_room_booking.service.RoomFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/room-features")
@RequiredArgsConstructor
public class RoomFeatureController {
    private final RoomFeatureService featureService;

    // List all features
    @GetMapping
    public ResponseEntity<List<RoomFeatureDto>> getAllFeatures() {
        return ResponseEntity.ok(featureService.getAllFeatures());
    }

    // (Admin) Create a feature
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomFeatureDto> createFeature(@RequestBody RoomFeatureDto featureDto) {
        return ResponseEntity.status(201).body(featureService.createFeature(featureDto));
    }

    // (Admin) Delete a feature
    @DeleteMapping("/{featureId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFeatureById(@PathVariable Long featureId) {
        featureService.deleteFeature(featureId);
        return ResponseEntity.noContent().build();
    }
}
