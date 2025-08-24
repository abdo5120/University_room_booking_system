package com.sprints.university_room_booking.controller;

import com.sprints.university_room_booking.dto.*;
import com.sprints.university_room_booking.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    // List all buildings
    @GetMapping
    public ResponseEntity<List<BuildingDto>> getAllBuildings() {
        return ResponseEntity.ok(buildingService.getAllBuildings());
    }

    // (Admin) Create a building
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BuildingDto> createBuilding(@RequestBody BuildingDto buildingDto) {
        return ResponseEntity.status(201).body(buildingService.createBuilding(buildingDto));
    }

    // (Admin) Delete a building
    @DeleteMapping("/{buildingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBuildingById(@PathVariable Long buildingId) {
        buildingService.deleteBuilding(buildingId);
        return ResponseEntity.noContent().build();
    }
}
