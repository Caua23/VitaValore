package com.VidaPlus.VitaValore.dto.produtos;


import com.VidaPlus.VitaValore.models.Resposta;
import com.VidaPlus.VitaValore.models.User;
import com.VidaPlus.VitaValore.models.enums.PerguntasEnum;

import java.util.List;

public class ComentarioDto {
    private String titulo;
    private String descricao;
    private PerguntasEnum perguntasEnum;

    private List<Resposta> respostas;

    private User users;


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



    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }
}
