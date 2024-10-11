package com.VidaPlus.VitaValore.repository;

import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.models.Produtos;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmpresasRepository extends CrudRepository<Empresas, Long> {

    List<Empresas> findByName(String name);

    List<Empresas> findByProdutos(Produtos produtos);
    Optional<Empresas> findByCnpj(String cnpj);
    Optional<Empresas> findByEmail(String email);

}
