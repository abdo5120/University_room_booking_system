package com.sprints.university_room_booking.service;

import com.sprints.university_room_booking.model.Department;
import com.sprints.university_room_booking.model.Role;
import com.sprints.university_room_booking.model.User;
import com.sprints.university_room_booking.repository.DepartmentRepository;
import com.sprints.university_room_booking.repository.RoleRepository;
import com.sprints.university_room_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Order(3) // Run after RoleInitializationService
public class DataInitializationService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Go to DataInitializationService, if you want sample Data");
        initializeDepartments();
        initializeUsers();
    }

    private void initializeDepartments() {
        if (departmentRepository.count() == 0) {
            Department computerScience = new Department();
            computerScience.setName("Computer Science");
            departmentRepository.save(computerScience);

            Department mathematics = new Department();
            mathematics.setName("Mathematics");
            departmentRepository.save(mathematics);

            Department physics = new Department();
            physics.setName("Physics");
            departmentRepository.save(physics);
        }
    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {
            Department csDepartment = departmentRepository.findAll().get(0);

            // Create Admin User
            User admin = new User();
            admin.setUniversityId("ADMIN001");
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@university.edu");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setDepartment(csDepartment);

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleRepository.findByName("ROLE_ADMIN").orElseThrow());
            admin.setRoles(adminRoles);

            userRepository.save(admin);

            // Create Professor User
            User professor = new User();
            professor.setUniversityId("PROF001");
            professor.setFirstName("John");
            professor.setLastName("Professor");
            professor.setEmail("professor@university.edu");
            professor.setPassword(passwordEncoder.encode("prof123"));
            professor.setDepartment(csDepartment);

            Set<Role> profRoles = new HashSet<>();
            profRoles.add(roleRepository.findByName("ROLE_PROFESSOR").orElseThrow());
            professor.setRoles(profRoles);

            userRepository.save(professor);

            // Create Student User
            User student = new User();
            student.setUniversityId("STU001");
            student.setFirstName("Jane");
            student.setLastName("Student");
            student.setEmail("student@university.edu");
            student.setPassword(passwordEncoder.encode("student123"));
            student.setDepartment(csDepartment);

            Set<Role> studentRoles = new HashSet<>();
            studentRoles.add(roleRepository.findByName("ROLE_STUDENT").orElseThrow());
            student.setRoles(studentRoles);

            userRepository.save(student);
        }
    }
}