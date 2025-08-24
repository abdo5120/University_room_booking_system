package com.sprints.university_room_booking.controller;

import com.sprints.university_room_booking.dto.*;
import com.sprints.university_room_booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    // (Student/Faculty) Request a booking
    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR')")
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        return ResponseEntity.status(201).body(bookingService.createBooking(bookingRequestDto));
    }

    // List own bookings (user) or all bookings (admin)
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<List<BookingResponseDto>> getBookings(/* filters as needed */) {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // Get booking details
    @GetMapping("/{bookingId}")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<BookingResponseDto> getBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    // Cancel a booking (user or admin)
    @PatchMapping("/{bookingId}/cancel")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        bookingService.deleteBookingById(bookingId);
        return ResponseEntity.ok().build();
    }

    // Approve a booking (admin)
    @PatchMapping("/{bookingId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveBooking(@PathVariable Long bookingId) {
        bookingService.approveBooking(bookingId);
        return ResponseEntity.ok().build();
    }

    // Reject a booking (admin)
    @PatchMapping("/{bookingId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectBooking(@PathVariable Long bookingId, @RequestBody BookingApprovalDto approvalDto) {
        bookingService.rejectBooking(bookingId, approvalDto);
        return ResponseEntity.ok().build();
    }

    // Get booking history (status changes)
    @GetMapping("/{bookingId}/history")
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<List<BookingHistoryDto>> getBookingHistory(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingHistory(bookingId));
    }
}
