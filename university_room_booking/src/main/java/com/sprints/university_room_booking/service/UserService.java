package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.dto.RoleDto;
import com.sprints.university_room_booking.dto.UserSummaryDto;
import com.sprints.university_room_booking.mapper.UserMapper;
import com.sprints.university_room_booking.model.Role;
import com.sprints.university_room_booking.model.User;
import com.sprints.university_room_booking.repository.RoleRepository;
import com.sprints.university_room_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public List<UserSummaryDto> getAllUsers()
    {
        List<User> users = userRepository.findAll();
        if(users.isEmpty())
            throw new RuntimeException("No users found");

        List<UserSummaryDto> userSummaries = new ArrayList<>();
        for(User user : users)
            userSummaries.add(userMapper.toSummary(user));
        return userSummaries;
    }

    public UserSummaryDto getUserById(Long userId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toSummary(user);
    }

    @Transactional
    public void updateUserRoles(Long userId,List<RoleDto> roles)
    {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found"));
        user.getRoles().clear();

        Set<Role> rolesSet = new HashSet<>();
        for(RoleDto role : roles)
            rolesSet.add(roleRepository.findById(role.getId())
                    .orElseThrow(()->new RuntimeException("Role not found")));

        user.setRoles(rolesSet);

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserSummaryDto getCurrentUserProfile()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        return userMapper.toSummary(user);
    }
}
