package com.VidaPlus.VitaValore.dto.empresa;

public class SaqueDto {

    private double valor;

    private String cnpj;

    public SaqueDto(double valor, String cnpj) {
        this.valor = valor;
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
