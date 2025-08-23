package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.dto.AuthResponseDto;
import com.sprints.university_room_booking.dto.LoginRequestDto;
import com.sprints.university_room_booking.dto.RegisterUserDto;
import com.sprints.university_room_booking.exception.ResourceAlreadyExistsException;
import com.sprints.university_room_booking.model.Department;
import com.sprints.university_room_booking.model.Role;
import com.sprints.university_room_booking.model.User;
import com.sprints.university_room_booking.repository.DepartmentRepository;
import com.sprints.university_room_booking.repository.RoleRepository;
import com.sprints.university_room_booking.repository.UserRepository;
import com.sprints.university_room_booking.security.JwtGenerator;
import com.sprints.university_room_booking.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;

    public AuthResponseDto login(LoginRequestDto loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Extract role names from user (remove ROLE_ prefix for JWT)
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getName().replace("ROLE_", ""))
                .collect(Collectors.toList());

        String token = jwtGenerator.generateToken(user.getId(), roles);

        return new AuthResponseDto(token, "Bearer", user.getEmail(), roles);
    }

    public AuthResponseDto registerUser(RegisterUserDto registerRequest, String neededRole) {
        String effectiveRole = (neededRole == null || neededRole.isBlank()) ? "ROLE_STUDENT" : neededRole.toUpperCase();

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ResourceAlreadyExistsException("Email is already taken!");
        }

        if (userRepository.existsByUniversityId(registerRequest.getUniversityId())) {
            throw new ResourceAlreadyExistsException("University ID is already taken!");
        }

        Department department = departmentRepository.findById(registerRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        User user = new User();
        user.setUniversityId(registerRequest.getUniversityId());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setDepartment(department);

        Role studentRole = roleRepository.findByName(effectiveRole)
                .orElseThrow(() -> new RuntimeException("Undefined Role"));

        Set<Role> roles = new HashSet<>();
        roles.add(studentRole);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        List<String> roleNames = savedUser.getRoles().stream()
                .map(role -> role.getName().replace("ROLE_", ""))
                .collect(Collectors.toList());

        String token = jwtGenerator.generateToken(savedUser.getId(), roleNames);

        return new AuthResponseDto(token, "Bearer", savedUser.getEmail(), roleNames);
    }

    public User getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}