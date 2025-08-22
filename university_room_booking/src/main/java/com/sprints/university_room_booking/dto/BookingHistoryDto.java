package com.sprints.university_room_booking.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookingHistoryDto {

    private Long id;
    private String status;
    private String reason;
    private LocalDateTime timestamp;
    private UserSummaryDto changedByUser;
}
