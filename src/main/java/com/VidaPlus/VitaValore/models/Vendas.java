package com.VidaPlus.VitaValore.models;



import com.VidaPlus.VitaValore.models.enums.StatusPagamento;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Random;

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

//    @ManyToOne
//    @JoinColumn(name = "comprador_id")
//    private User comprador;

    private int quantidade;

    private LocalDate dataVenda;

    private double valorPago;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;



//    @NotNull
//    public User getComprador() {
//        return comprador;
//    }
//
//    public void setComprador(@NotNull User comprador) {
//        this.comprador = comprador;
//    }

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
    public static LocalDate gerarDataAleatoria(int anoInicial, int anoFinal) {
        Random random = new Random();


        int ano = random.nextInt(anoFinal - anoInicial + 1) + anoInicial;


        int mes = random.nextInt(4) + 9;


        int dia = random.nextInt(LocalDate.of(ano, mes, 1).lengthOfMonth()) + 1;


        return LocalDate.of(ano, mes, dia);
    }

}
