package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.model.Role;
import com.sprints.university_room_booking.model.User;
import com.sprints.university_room_booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock UserRepository userRepository;
    @InjectMocks CustomUserDetailsService uds;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_whenExists_returnsSpringUser() {
        User u = new User();
        u.setEmail("a@u");
        u.setPassword("pw");
        u.setRoles(Set.of(new Role(1L,"ROLE_ADMIN")));
        when(userRepository.findByEmail("a@u")).thenReturn(Optional.of(u));

        UserDetails details = uds.loadUserByUsername("a@u");
        assertEquals("a@u", details.getUsername());
        assertEquals("pw", details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_notFound_throws() {
        when(userRepository.findByEmail("x")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> uds.loadUserByUsername("x"));
    }
}