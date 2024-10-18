package com.VidaPlus.VitaValore.dto.user;

import com.VidaPlus.VitaValore.models.enums.PerguntasEnum;

public class CreateComentario {

    private String titulo;
    private String descricao;
    private int notaAvaliacao;
    private long produtosId;

    public CreateComentario(String titulo, String descricao, int notaAvaliacao, long produtosId) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.notaAvaliacao = notaAvaliacao;
        this.produtosId = produtosId;
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

    public long getProdutosId() {
        return produtosId;
    }

    public void setProdutosId(long produtosId) {
        this.produtosId = produtosId;
    }
}
