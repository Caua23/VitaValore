package com.VidaPlus.VitaValore.repository;

import com.VidaPlus.VitaValore.models.Comentario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, Long> {

}
