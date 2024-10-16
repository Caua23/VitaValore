package com.VidaPlus.VitaValore.repository;

import com.VidaPlus.VitaValore.models.Vendas;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VendasRepository extends CrudRepository<Vendas, Long> {
    @Query("SELECT v FROM Vendas v WHERE v.empresas.id = :empresaId ORDER BY v.dataVenda DESC")
    List<Vendas> findUltimasVendasEmpresa(@Param("empresaId") Long id, Pageable pageable);

}
