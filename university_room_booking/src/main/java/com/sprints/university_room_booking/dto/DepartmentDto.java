package com.sprints.university_room_booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentDto {

    @NotNull(message = "department id cannot be null")
    private Long id;

    @NotBlank(message = "department name cannot be blank")
    @Size(min = 3, message = "department name must be at least 3 letters.")
    private String name;
}
