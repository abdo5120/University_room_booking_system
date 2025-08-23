package com.sprints.university_room_booking.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Custom UserDetails implementation that holds user information from JWT
 */
public class JwtUserPrincipal implements UserDetails {

    private final Long userId;
    private final List<String> roles;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUserPrincipal(Long userId, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.roles = roles;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null; // Not needed for JWT
    }

    @Override
    public String getUsername() {
        return userId.toString(); // Use userId as username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}