package com.sprints.university_room_booking.mapper;

import com.sprints.university_room_booking.dto.RegisterUserDto;
import com.sprints.university_room_booking.dto.UserSummaryDto;
import com.sprints.university_room_booking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserMapper();
    }

    @Test
    void toSummary_nullInput_returnsNull() {
        assertNull(mapper.toSummary(null));
    }

    @Test
    void toSummary_mapsFields() {
        User u = new User();
        u.setId(3L);
        u.setFirstName("F");
        u.setLastName("L");
        u.setEmail("e@u");

        UserSummaryDto dto = mapper.toSummary(u);
        assertNotNull(dto);
        assertEquals(3L, dto.getId());
        assertEquals("F", dto.getFirstName());
        assertEquals("L", dto.getLastName());
        assertEquals("e@u", dto.getEmail());
    }

    @Test
    void toEntity_nullInput_returnsNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    void toEntity_mapsFields() {
        RegisterUserDto r = new RegisterUserDto();
        r.setUniversityId("U1");
        r.setFirstName("Fn");
        r.setLastName("Ln");
        r.setEmail("x@y");
        r.setPassword("pw");

        User u = mapper.toEntity(r);
        assertNotNull(u);
        assertNull(u.getId());
        assertEquals("U1", u.getUniversityId());
        assertEquals("Fn", u.getFirstName());
        assertEquals("Ln", u.getLastName());
        assertEquals("x@y", u.getEmail());
        assertEquals("pw", u.getPassword());
    }
}