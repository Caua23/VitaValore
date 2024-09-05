package com.VidaPlus.VitaValore.dto.produtos;

import com.VidaPlus.VitaValore.models.Empresas;
import jakarta.validation.constraints.NotBlank;


public class Produtos {

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

    private Empresas empresa;

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

    public Empresas getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresas empresa) {
        this.empresa = empresa;
    }
}
