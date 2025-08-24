package com.sprints.university_room_booking.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utility class to easily access current user information in controllers
 */
@Component
public class SecurityUtils {
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserPrincipal) {
            JwtUserPrincipal userPrincipal = (JwtUserPrincipal) authentication.getPrincipal();
            return userPrincipal.getUserId();
        }
        throw new RuntimeException("No authenticated user found");
    }

    public static List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof JwtUserPrincipal) {
            JwtUserPrincipal userPrincipal = (JwtUserPrincipal) authentication.getPrincipal();
            return userPrincipal.getRoles();
        }
        throw new RuntimeException("No authenticated user found");
    }

    public static boolean hasRole(String role) {
        List<String> roles = getCurrentUserRoles();
        return roles.contains(role.toUpperCase());
    }

    public static boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public static boolean isStudent() {
        return hasRole("STUDENT");
    }

    public static boolean isProfessor() {
        return hasRole("PROFESSOR");
    }
}