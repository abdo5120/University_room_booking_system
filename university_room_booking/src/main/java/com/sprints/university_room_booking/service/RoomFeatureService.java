package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.dto.RoomFeatureDto;
import com.sprints.university_room_booking.mapper.RoomFeatureMapper;
import com.sprints.university_room_booking.model.RoomFeature;
import com.sprints.university_room_booking.repository.RoomFeatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomFeatureService
{
    private RoomFeatureRepository roomFeatureRepository;
    private RoomFeatureMapper roomFeatureMapper;

    public List<RoomFeatureDto> getAllFeatures()
    {
        List<RoomFeature> roomFeature = roomFeatureRepository.findAll();
        if(roomFeature.isEmpty())
            throw new RuntimeException("Room feature is empty");
        return roomFeatureMapper.toDTOList(roomFeature);
    }

    @Transactional
    public RoomFeatureDto createFeature(RoomFeatureDto roomFeatureDto)
    {
        if(roomFeatureRepository.existsById(roomFeatureDto.getId()))
            throw new RuntimeException("Room feature already exists");
        RoomFeature roomFeature = roomFeatureMapper.toEntity(roomFeatureDto);
        roomFeatureRepository.save(roomFeature);
        return roomFeatureMapper.toDTO(roomFeature);
    }

    @Transactional
    public void deleteFeature(Long id)
    {
        if (roomFeatureRepository.findById(id).isPresent())
            roomFeatureRepository.deleteById(id);
        else
            throw new RuntimeException("Room feature is not found");
    }
}
