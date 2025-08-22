package com.sprints.university_room_booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingRequestDto {

    @NotNull(message = "Room ID is required")
    private Long roomId;

    @NotNull(message = "Start time is required")
    @FutureOrPresent(message = "Booking start time cannot be in the past")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @FutureOrPresent(message = "Booking end time cannot be in the past")
    private LocalDateTime endTime;

    @NotBlank(message = "Purpose of the booking is required")
    private String purpose;
}
