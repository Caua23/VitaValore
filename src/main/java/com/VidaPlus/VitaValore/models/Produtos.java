    package com.VidaPlus.VitaValore.models;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

@Entity
@Table(name = "produtos")
public class Produtos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "O nome é obrigatório")
    private String name;

    @NotEmpty(message = "O preço é obrigatório")
    private double preco;

    @NotEmpty(message = "A imagem é obrigatório")
    private String imagem;

    @NotEmpty(message = "A descrição é obrigatória")
    private String descricao;

    @NotEmpty(message = "A marca é obrigatória")
    private String marca;

    @ManyToOne
    private Empresas empresa;



    public Empresas getEmpresa() {
        return empresa;
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
