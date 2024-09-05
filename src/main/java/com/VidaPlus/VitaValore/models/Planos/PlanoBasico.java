package com.VidaPlus.VitaValore.models.Planos;

public class PlanoBasico extends Plano {
    public PlanoBasico() {
        this.setName("Plano Basico");
        this.setPreco(59.90);
        this.setLimite(5);
        this.setDuracao(30);
        this.setDescricao("Um plano básico que permite a postagem de até cinco produtos, com duração de um 30 dias.");
    }
}
