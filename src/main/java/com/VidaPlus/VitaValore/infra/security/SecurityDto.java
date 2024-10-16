package com.VidaPlus.VitaValore.infra.security;

import com.VidaPlus.VitaValore.models.enums.Role;

public class SecurityDto {
    private Role role;
    private String email;

    public SecurityDto(Role role, String email) {
        this.role = role;
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
