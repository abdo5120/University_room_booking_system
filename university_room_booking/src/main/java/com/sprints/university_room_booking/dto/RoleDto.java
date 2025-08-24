package com.sprints.university_room_booking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleDto {
    
    @NotBlank(message = "Role id cannot be blank")
    private Long id;

    @NotBlank(message = "Role name cannot be blank")
    private String name;
}
