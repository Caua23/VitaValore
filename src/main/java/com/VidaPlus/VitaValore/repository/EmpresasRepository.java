package com.VidaPlus.VitaValore.repository;

import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.models.Produtos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmpresasRepository extends CrudRepository<Empresas, Long> {

    List<Empresas> findByName(String name);

    List<Empresas> findByProdutos(Produtos produtos);
    Optional<Empresas> findByCnpj(String cnpj);
    Optional<Empresas> findByEmail(String email);

}
