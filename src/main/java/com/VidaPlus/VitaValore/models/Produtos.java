package com.VidaPlus.VitaValore.models;


import com.VidaPlus.VitaValore.models.enums.ProdutosCategoria;
import com.VidaPlus.VitaValore.models.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

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
    @Column(unique = true)
    private String imagem;

    @NotEmpty(message = "A descrição é obrigatória")
    @Column(unique = true, length = 700)
    private String descricao;

    @NotEmpty(message = "A marca é obrigatória")
    private String marca;

    @ManyToOne()
    @JoinColumn(name = "empresa_id")
    @JsonManagedReference
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    private Comentario comentario;

    @ElementCollection(targetClass = ProdutosCategoria.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Set<ProdutosCategoria> produtosCategoria;




    public Set<ProdutosCategoria> getProdutosCategoria() {
        return produtosCategoria;
    }

    public void setProdutosCategoria(Set<ProdutosCategoria> produtosCategoria) {
        this.produtosCategoria = produtosCategoria;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Produtos(Empresa empresa) {
        this.empresa = empresa;
    }


    public Produtos() {

    }

    public Optional<Empresa> getEmpresa() {
        return Optional.ofNullable(empresa);
    }

    public void setEmpresa(Empresa empresa) {
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
