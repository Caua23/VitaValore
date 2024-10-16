package com.VidaPlus.VitaValore.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "Resposta")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String titulo;
    private String resposta;

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    @JsonBackReference
    private Comentario comentario;

    @ManyToOne
    @JoinColumn(name = "empresas_id")
    private Empresa empresas;


    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

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

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Empresa getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresa empresas) {
        this.empresas = empresas;
    }
}
