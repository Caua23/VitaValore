package com.VidaPlus.VitaValore.models;

import com.VidaPlus.VitaValore.models.enums.PerguntasEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(max = 100)
    private String titulo;

    @NotNull
    private String descricao;

    @NotNull
    private int notaAvaliacao;

    @Enumerated(EnumType.STRING)
    private PerguntasEnum perguntasEnum = PerguntasEnum.NAORESPONDIDA;

    @OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL, orphanRemoval = true) // Correção aqui
    private List<Resposta> respostas;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produtos produto;

    public Comentario() {}

    public Comentario(String titulo, String descricao, int notaAvaliacao, PerguntasEnum perguntasEnum, User user, Produtos produto) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.notaAvaliacao = notaAvaliacao;
        this.perguntasEnum = perguntasEnum;
        this.user = user;
        this.produto = produto;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public void setNotaAvaliacao(int notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }

    public PerguntasEnum getPerguntasEnum() {
        return perguntasEnum;
    }

    public void setPerguntasEnum(PerguntasEnum perguntasEnum) {
        this.perguntasEnum = perguntasEnum;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", notaAvaliacao=" + notaAvaliacao +
                ", perguntasEnum=" + perguntasEnum +
                ", user=" + user +
                ", produto=" + produto +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comentario)) return false;
        Comentario that = (Comentario) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
