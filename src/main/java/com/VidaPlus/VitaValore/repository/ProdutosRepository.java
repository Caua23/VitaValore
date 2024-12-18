package com.VidaPlus.VitaValore.repository;

import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.models.enums.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//import java.util.Optional;
@Repository
public interface ProdutosRepository extends CrudRepository<Produtos, Long> {

    List<Produtos> findByName(String name);

    List<Produtos> findByMarca(String marca);

    List<Produtos> findByEmpresaId(Long id);

    List<Produtos> findByStatus(Status status);

    Optional<Produtos> findByDescricaoAndImagem(String descricao, String imagem);


}
