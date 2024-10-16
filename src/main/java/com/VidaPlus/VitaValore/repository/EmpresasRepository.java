package com.VidaPlus.VitaValore.repository;

import com.VidaPlus.VitaValore.models.Empresa;
import com.VidaPlus.VitaValore.models.Produtos;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EmpresasRepository extends CrudRepository<Empresa, Long> {

    List<Empresa> findByName(String name);

    List<Empresa> findByProdutos(Produtos produtos);
    Optional<Empresa> findByCnpj(String cnpj);
    Optional<Empresa> findByEmail(String email);

}
