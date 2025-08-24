package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.dto.DepartmentDto;
import com.sprints.university_room_booking.model.Department;
import com.sprints.university_room_booking.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService
{
    private final DepartmentRepository departmentRepository;

    public List<DepartmentDto> getAllDepartments()
    {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        for(Department department : departments)
        {
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setId(department.getId());
            departmentDto.setName(department.getName());
            departmentDtos.add(departmentDto);
        }
        return departmentDtos;
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto)
    {
        if(departmentRepository.existsById(departmentDto.getId()))
            throw new RuntimeException("Department already exists!");
        else
        {
            Department department = new Department();
            department.setName(departmentDto.getName());
            departmentRepository.save(department);
        }
        return departmentDto;
    }

    public void deleteDepartmentById(Long id)
    {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found!"));
        departmentRepository.delete(department);
    }
}
