package com.VidaPlus.VitaValore.models;

import com.VidaPlus.VitaValore.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Set;
@Entity
@Table(name = "admins")
public class Admins implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @NotBlank(message = "Email obrigatório")
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;


//    private Role roles;
//
//    public Role getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Role roles) {
//        this.roles = roles;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
