package com.VidaPlus.VitaValore.repository;

import com.VidaPlus.VitaValore.models.Planos.Plano;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlanoRepository extends CrudRepository<Plano, Long> {
    Optional<Plano> findByLimite(int limite);
    Optional<Plano> findById(Plano planoAtual);
}
