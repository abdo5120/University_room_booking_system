package com.sprints.university_room_booking.mapper;

import com.sprints.university_room_booking.dto.BuildingDto;
import com.sprints.university_room_booking.model.Building;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuildingMapper {

    public BuildingDto toDTO(Building building) {
        if (building == null) return null;

        BuildingDto dto = new BuildingDto();
        dto.setId(building.getId());
        dto.setName(building.getName());
        dto.setAddress(building.getAddress());
        return dto;
    }

    public Building toEntity(BuildingDto dto) {
        if (dto == null) return null;

        Building b = new Building();
        b.setName(dto.getName());
        b.setAddress(dto.getAddress());
        return b;
    }

    public Building updateEntityFromDto(Building existing, BuildingDto dto) {
        if (existing == null || dto == null) return existing;
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
        return existing;
    }

    public List<BuildingDto> toDTOList(List<Building> list) {
        if (list == null) return null;
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Building> toEntityList(List<BuildingDto> list) {
        if (list == null) return null;
        return list.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
