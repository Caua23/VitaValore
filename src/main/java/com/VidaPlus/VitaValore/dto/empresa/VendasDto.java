package com.VidaPlus.VitaValore.dto.empresa;

import com.VidaPlus.VitaValore.models.Vendas;

import java.util.List;

public class VendasDto {
    private String date;
    private int compras;
    private int avaliacao;

    public VendasDto(List<Vendas> vendas, List<String> avaliacoes) {
        if (vendas != null && !vendas.isEmpty()) {
            this.date = vendas.get(0).getDataVenda().toString();
            this.compras = vendas.size();
        } else {
            this.date = "Sem data"; // ou outra lógica para tratamento
            this.compras = 0;
        }

        this.avaliacao = (avaliacoes != null) ? avaliacoes.size() : 0;
    }

    // Getters e Setters
    public String getDate() {
        return date;
    }

    public int getCompras() {
        return compras;
    }

    public int getAvaliacao() {
        return avaliacao;
    }
}
