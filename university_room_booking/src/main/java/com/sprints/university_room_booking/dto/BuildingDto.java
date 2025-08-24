package com.sprints.university_room_booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuildingDto {
    
    @NotNull(message = "building id cannot be null")
    private Long id;

    @NotBlank(message = "building name cannot be blank")
    private String name;

    @NotBlank(message = "building address cannot be blank")
    private String address;
}
