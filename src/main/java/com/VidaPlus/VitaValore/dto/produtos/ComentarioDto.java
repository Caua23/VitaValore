package com.VidaPlus.VitaValore.dto.produtos;

import com.VidaPlus.VitaValore.models.Resposta;
import com.VidaPlus.VitaValore.models.Users;
import com.VidaPlus.VitaValore.models.enums.PerguntasEnum;

import java.util.List;

public class ComentarioDto {
    private String titulo;
    private String descricao;
    private PerguntasEnum perguntasEnum;

    private List<Resposta> respostas;

    private Users users;


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

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
