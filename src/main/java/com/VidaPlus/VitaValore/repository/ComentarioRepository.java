package com.VidaPlus.VitaValore.repository;

import com.VidaPlus.VitaValore.models.Comentario;
import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.models.enums.PerguntasEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, Long> {

    List<Comentario> findAllByPerguntasEnum(PerguntasEnum perguntasEnum);


    @Query("SELECT c.produto FROM Comentario c WHERE c.produto.empresa.id = :empresaId GROUP BY c.produto ORDER BY COUNT(c) DESC")
    List<Produtos> findProdutoComMaisAvaliacoes(@Param("empresaId") Long empresaId, Pageable pageable);
}
