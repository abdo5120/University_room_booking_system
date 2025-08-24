package com.sprints.university_room_booking.mapper;

import com.sprints.university_room_booking.dto.RegisterUserDto;
import com.sprints.university_room_booking.dto.UserSummaryDto;
import com.sprints.university_room_booking.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserSummaryDto toSummary(User user) {
        if (user == null) return null;

        UserSummaryDto dto = new UserSummaryDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public User toEntity(RegisterUserDto dto) {
        if (dto == null) return null;

        User u = new User();
        u.setUniversityId(dto.getUniversityId());
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        return u;
    }
}
