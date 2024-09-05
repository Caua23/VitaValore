package com.VidaPlus.VitaValore.models.Planos;

import jakarta.persistence.Entity;

@Entity
public class PlanoFree extends Plano {

    public PlanoFree() {
        this.setName("Plano Free");
        this.setPreco(0);
        this.setLimite(1);
        this.setDuracao(30);
        this.setDescricao("Plano Gratuito, que permite a criação de um produto com validade de 30 dias. Após esse período, o produto será removido e será necessário optar por um plano pago para continuar com a publicação.");
    }


}
