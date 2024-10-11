package com.VidaPlus.VitaValore.models;

import com.VidaPlus.VitaValore.models.enums.PerguntasEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Comentarios")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String titulo;
    private String descricao;


    @Enumerated(EnumType.STRING)
    private PerguntasEnum perguntasEnum = PerguntasEnum.NAORESPODIDA;

    @OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Resposta> resposta ;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private Users user;



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

    public PerguntasEnum getPerguntasEnum() {
        return perguntasEnum;
    }

    public void setPerguntasEnum(PerguntasEnum perguntasEnum) {
        this.perguntasEnum = perguntasEnum;
    }

    public List<Resposta> getResposta() {
        return resposta;
    }

    public void setResposta(List<Resposta> resposta) {
        this.resposta = resposta;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }



}
