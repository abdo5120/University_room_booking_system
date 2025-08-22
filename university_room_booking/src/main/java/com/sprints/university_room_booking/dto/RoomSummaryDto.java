package com.sprints.university_room_booking.dto;

import lombok.Data;

@Data
public class RoomSummaryDto {
    private Long id;
    private String roomNumber;
    private String buildingName;
}
