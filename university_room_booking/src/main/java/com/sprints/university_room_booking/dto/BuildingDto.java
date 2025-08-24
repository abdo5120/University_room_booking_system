package com.sprints.university_room_booking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BuildingDto {
    
    @NotBlank(message = "building id cannot be blank")
    private Long id;

    @NotBlank(message = "building name cannot be blank")
    private String name;

    @NotBlank(message = "building address cannot be blank")
    private String address;
}
