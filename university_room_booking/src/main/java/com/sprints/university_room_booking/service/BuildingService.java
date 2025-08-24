package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.dto.BuildingDto;
import com.sprints.university_room_booking.mapper.BuildingMapper;
import com.sprints.university_room_booking.model.Building;
import com.sprints.university_room_booking.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingService
{
    private final BuildingRepository buildingRepository;
    private final BuildingMapper buildingMapper;

    public List<BuildingDto> getAllBuildings()
    {
        List<Building> buildings = buildingRepository.findAll();
        if(buildings.isEmpty())
            throw new RuntimeException("buildings not found");
        buildingMapper.toDTOList(buildings);
        return buildingMapper.toDTOList(buildings);
    }

    public BuildingDto createBuilding(BuildingDto buildingDto)
    {
        if(buildingRepository.existsById(buildingDto.getId()))
            throw new RuntimeException("building already exists");
        buildingRepository.saveAndFlush(buildingMapper.toEntity(buildingDto));
        return buildingDto;
    }

    public void deleteBuilding(Long buildingId)
    {
        if(!buildingRepository.existsById(buildingId))
            throw new RuntimeException("building not found");
        buildingRepository.deleteById(buildingId);
    }
}
