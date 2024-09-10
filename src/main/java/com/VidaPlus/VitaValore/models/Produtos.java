    package com.VidaPlus.VitaValore.models;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Optional;

@Entity
@Table(name = "produtos")
public class Produtos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "O nome é obrigatório")
    private String name;

    private double preco;

    @NotEmpty(message = "A imagem é obrigatório")
    @Column(unique = true) // para não ter problemas de duplicação de produto
    private String imagem;

    @NotEmpty(message = "A descrição é obrigatória")
    @Column(unique = true)// para não ter problemas de duplicação de produto 
    private String descricao;

    @NotEmpty(message = "A marca é obrigatória")
    private String marca;

    @ManyToOne()
    @JoinColumn(name = "empresa_id" )
    @JsonManagedReference
    private Empresas empresa;


    public Produtos(Empresas empresa) {
        this.empresa = empresa;
    }


    public Produtos() {

    }

    public Optional<Empresas> getEmpresa() {
        return Optional.ofNullable(empresa);
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
