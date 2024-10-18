package com.VidaPlus.VitaValore.dto.user;

import com.VidaPlus.VitaValore.models.Empresa;
import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.models.enums.StatusPagamento;
import jakarta.persistence.*;

import java.time.LocalDate;

public class CreateCompra {

    private long produtosId;

    private long empresasId;

    private int quantidade;

    private double valorPago;

    private StatusPagamento statusPagamento ;

    public CreateCompra(long produtosId, long empresasId, int quantidade, double valorPago, StatusPagamento statusPagamento) {
        this.produtosId = produtosId;
        this.empresasId = empresasId;
        this.quantidade = quantidade;
        this.valorPago = valorPago;
        this.statusPagamento = statusPagamento;
    }

    public long getProdutosId() {
        return produtosId;
    }

    public void setProdutosId(long produtosId) {
        this.produtosId = produtosId;
    }

    public long getEmpresasId() {
        return empresasId;
    }

    public void setEmpresasId(long empresasId) {
        this.empresasId = empresasId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}
