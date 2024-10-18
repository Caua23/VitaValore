package com.VidaPlus.VitaValore.models;


import com.VidaPlus.VitaValore.models.enums.StatusPagamento;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.UUID;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
@Entity
public class Vendas implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "produtos_id")
    private Produtos produtos;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empresa_id")
    private Empresa empresas;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private User comprador;

    private int quantidade;

    private LocalDate dataVenda;

    private double valorPago;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

    @NotNull
    public User getComprador() {
        return comprador;
    }

    public void setComprador(@NotNull User comprador) {
        this.comprador = comprador;
    }

    public long getId() {
        return id;
    }

    public void setId(long  id) {
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
