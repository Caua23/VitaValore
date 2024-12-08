package com.VidaPlus.VitaValore.models.Planos;
import jakarta.persistence.Entity;


@Entity

public class PlanoPersonalizado extends Plano{

    public PlanoPersonalizado() {
        this.setName("Plano Personalizado");
        this.setPreco(599.90);
        this.setLimite(50);
        this.setDuracao(120);
        this.setDescricao("Um plano premium personalizado que permite a postagem de até cinquenta produtos, com duração estendida de 120 dias. Ideal para empresas de médio porte ou com grande variedade de produtos.");
    }
}
