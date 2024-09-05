package com.VidaPlus.VitaValore.models.Planos;

public class PlanoPremium extends Plano {
    public PlanoPremium() {
        this.setName("Plano Premium");
        this.setPreco(300.00);
        this.setLimite(20);
        this.setDuracao(90);
        this.setDescricao("Um plano premium que permite a postagem de até vinte e cinco produtos, com duração de 90 dias.");
    }
}
