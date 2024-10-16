package com.VidaPlus.VitaValore.models;


import com.VidaPlus.VitaValore.models.enums.StatusPagamento;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.UUID;

import java.io.Serializable;
import java.time.LocalDate;
@Entity
public class Vendas implements Serializable {
    @UUID
    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produtos_id")
    private Produtos produtos;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id")
    private Empresa empresas;

    private int quantidade;

    private LocalDate dataVenda;

    private double valorPago;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public Empresa getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresa empresas) {
        this.empresas = empresas;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
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