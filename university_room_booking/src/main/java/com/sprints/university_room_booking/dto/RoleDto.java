package com.sprints.university_room_booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoleDto {
    
    @NotNull(message = "Role id cannot be null")
    private Long id;

    @NotBlank(message = "Role name cannot be blank")
    private String name;
}
