package com.sprints.university_room_booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomFeatureDto {
    
    @NotNull(message = "feature id cannot be null.")
    private Long id;
    
    @NotBlank(message = "feature name cannot be blank.")
    private String name;
}
