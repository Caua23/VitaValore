package com.VidaPlus.VitaValore.repository;

import com.VidaPlus.VitaValore.models.Produtos;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
//import java.util.Optional;

public interface ProdutosRepository extends CrudRepository<Produtos, Long> {

    List<Produtos> findByName(String name);

    List<Produtos> findByMarca(String marca);

    List<Produtos> findByEmpresaId(Long id);

    Optional<Produtos> findByDescricaoAndImagem(String descricao, String imagem);


}
