package com.sprints.university_room_booking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomFeatureDto {
    
    @NotBlank(message = "feature id cannot be blank.")
    private Long id;
    
    @NotBlank(message = "feature name cannot be blank.")
    private String name;
}
