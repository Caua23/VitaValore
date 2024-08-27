package com.VidaPlus.VitaValore.models;

import jakarta.persistence.*;
import com.VidaPlus.VitaValore.models.Produtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CNPJ;

import java.io.Serializable;
import java.util.List;

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

    @OneToMany(mappedBy = "empresa")
    private List<Produtos> produtos;

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
