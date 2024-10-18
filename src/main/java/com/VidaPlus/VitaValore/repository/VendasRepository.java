package com.VidaPlus.VitaValore.repository;


import com.VidaPlus.VitaValore.models.Vendas;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VendasRepository extends CrudRepository<Vendas, Long> {
    @Query("SELECT v.produtos, COUNT(v) AS totalVendas FROM Vendas v WHERE v.empresas.id = :empresaId GROUP BY v.produtos ORDER BY totalVendas DESC")
    List<Object[]> findProdutoMaisVendido(@Param("empresaId") Long empresaId, Pageable pageable);
    @Query("SELECT v FROM Vendas v WHERE v.empresas.id = :empresaId ORDER BY v.dataVenda DESC")
    List<Vendas> findUltimasVendasEmpresa(@Param("empresaId") Long id, Pageable pageable);


    @Query("SELECT YEAR(v.dataVenda) as ano, MONTH(v.dataVenda) as mes, DAY(v.dataVenda) as dia, COUNT(v) as totalVendas " +
            "FROM Vendas v " +
            "WHERE v.empresas.id = :empresaId AND v.dataVenda >= :dataInicio " +
            "GROUP BY YEAR(v.dataVenda), MONTH(v.dataVenda), DAY(v.dataVenda) " +
            "ORDER BY ano DESC, mes DESC, dia DESC")
    List<Object[]> findVendasUltimosTresMeses(@Param("empresaId") Long empresaId, @Param("dataInicio") LocalDate dataInicio);

    @Query("SELECT YEAR(v.dataVenda) as ano, MONTH(v.dataVenda) as mes, COUNT(v) as totalVendas, SUM(v.valorPago) as totalValor " + "FROM Vendas v " + "WHERE v.empresas.id = :empresaId AND v.dataVenda >= :dataInicio " + "GROUP BY YEAR(v.dataVenda), MONTH(v.dataVenda) " + "ORDER BY ano DESC, mes DESC")
    List<Object[]> buscarVendasPorMes(@Param("empresaId") Long empresaId, @Param("dataInicio") LocalDate dataInicio);


}
