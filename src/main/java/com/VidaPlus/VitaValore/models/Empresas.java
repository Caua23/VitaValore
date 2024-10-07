package com.VidaPlus.VitaValore.models;

import com.VidaPlus.VitaValore.models.Planos.Plano;
import com.VidaPlus.VitaValore.models.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "empresas")
public class Empresas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Nome obrigatório")
    private String name;

    @NotEmpty(message = "Email obrigatório")
    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "CNPJ obrigatório")
    @Column(unique = true)
    private String cnpj;

    @NotEmpty(message = "Senha obrigatória")
    private String password;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Produtos> produtos;

    @ManyToOne()
    @JoinColumn(name = "plano_id")
    @JsonBackReference
    private Plano planoAtual;

//    private Role roles;
//
//    public Role getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Role roles) {
//        this.roles = roles;
//    }

    public Plano getPlanoAtual() {
        return planoAtual;
    }

    public void setPlanoAtual(Plano planoAtual) {
        this.planoAtual = planoAtual;
    }

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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Produtos> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produtos> produtos) {
        this.produtos = produtos;
    }
}
