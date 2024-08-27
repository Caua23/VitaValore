package com.VidaPlus.VitaValore.dto;


import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;


public class CreateProdutoDto {

    private long id;

    @NotBlank
    private String name;

    private double preco;

    @NotBlank
    private String imagem;

    @NotBlank
    private String descricao;

    @NotBlank
    private String marca;

    @NotBlank
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


