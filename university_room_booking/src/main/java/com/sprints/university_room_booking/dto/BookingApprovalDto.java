package com.sprints.university_room_booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingApprovalDto {

    @NotNull(message = "A new status must be provided")
    private String status; // Expects "APPROVED" or "REJECTED"

    private String reason; // Optional, but useful for rejections
}