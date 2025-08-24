package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.dto.RoleDto;
import com.sprints.university_room_booking.model.Role;
import com.sprints.university_room_booking.repository.RoleRepository;
import com.sprints.university_room_booking.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService
{
    private final RoleRepository roleRepository;

    public List<RoleDto> getAllRoles()
    {
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        for(Role role : roles)
        {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }
}
