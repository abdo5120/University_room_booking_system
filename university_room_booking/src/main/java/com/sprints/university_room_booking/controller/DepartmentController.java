package com.sprints.university_room_booking.controller;

import com.sprints.university_room_booking.dto.DepartmentDto;
import com.sprints.university_room_booking.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    // List all departments
    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'PROFESSOR', 'ADMIN')")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // (Admin) Create a department
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        return ResponseEntity.status(201).body(departmentService.createDepartment(departmentDto));
    }

    // (Admin) Delete a department
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.noContent().build();
    }
}
