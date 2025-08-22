package com.sprints.university_room_booking.dto;

import lombok.Data;

@Data
public class UserSummaryDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
