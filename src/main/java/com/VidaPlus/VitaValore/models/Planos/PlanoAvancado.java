package com.VidaPlus.VitaValore.models.Planos;

import jakarta.persistence.Entity;

@Entity
public class PlanoAvancado extends Plano {

    public PlanoAvancado() {
        this.setName("Plano Avancado");
        this.setPreco(149.90);
        this.setLimite(10);
        this.setDuracao(60);
        this.setDescricao("Um plano avançado que permite a postagem de até dez produtos, com duração de 60 dias.");
    }
}
