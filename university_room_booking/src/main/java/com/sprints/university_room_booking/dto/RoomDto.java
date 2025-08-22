package com.sprints.university_room_booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Set;

@Data
public class RoomDto {

    private Long id;

    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Building ID is required")
    private Long buildingId;

    // Can be null for general-purpose rooms
    private Long departmentId;

    // A set of IDs for the features this room has
    private Set<Long> featureIds;
}
