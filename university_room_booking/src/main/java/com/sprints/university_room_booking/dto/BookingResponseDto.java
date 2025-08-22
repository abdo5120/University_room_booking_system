package com.sprints.university_room_booking.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingResponseDto {

    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private String status;

    // Nested DTOs for related information
    private RoomSummaryDto room;
    private UserSummaryDto user;
}
