package com.sprints.university_room_booking.controller;

import com.sprints.university_room_booking.dto.*;
import com.sprints.university_room_booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // List all rooms (with optional filters)
    @GetMapping
    public ResponseEntity<List<RoomSummaryDto>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    // Get room details
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    // Get available time slots for a room in a date range
    @GetMapping("/{roomId}/availability")
    public ResponseEntity<?> getRoomAvailability(@PathVariable Long roomId,
                                                 @RequestParam LocalDateTime startDate,
                                                 @RequestParam LocalDateTime endDate) {
        // ... call service ...
        return ResponseEntity.ok(roomService.getRoomAvailability(roomId, startDate, endDate));
    }

    // (Admin) Create a new room
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomService.createRoom(roomDto));
    }

    // (Admin) Update room details
    @PatchMapping("/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomDto> updateRoomById(@PathVariable Long roomId, @RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomService.updateRoom(roomId, roomDto));
    }

    // (Admin) Delete a room (with conflict check)
    @DeleteMapping("/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRoomById(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    // (Admin) Attach features to a room
    @PostMapping("/{roomId}/features")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> attachFeatures(@PathVariable Long roomId, @RequestBody List<Long> featureIds) {
        roomService.attachFeatures(roomId, featureIds);
        return ResponseEntity.ok().build();
    }

    // (Admin) Detach a feature from a room
    @DeleteMapping("/{roomId}/features/{featureId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> detachFeature(@PathVariable Long roomId, @PathVariable Long featureId) {
        roomService.detachFeature(roomId, featureId);
        return ResponseEntity.noContent().build();
    }
}
