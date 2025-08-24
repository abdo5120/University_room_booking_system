package com.sprints.university_room_booking.controller;

import com.sprints.university_room_booking.dto.BookingHistoryDto;
import com.sprints.university_room_booking.service.BookingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/booking-history")
@RequiredArgsConstructor
public class BookingHistoryController {
    private final BookingHistoryService bookingHistoryService;

    // Admin: List all booking history (optionally with filters)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingHistoryDto>> getAllHistory() {
        return ResponseEntity.ok(bookingHistoryService.getAllHistory());
    }

    // Admin: Get a single booking history entry
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingHistoryDto> getHistoryById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingHistoryService.getHistoryById(id));
    }
}
