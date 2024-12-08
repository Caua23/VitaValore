package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.models.Empresa;
import com.VidaPlus.VitaValore.models.Planos.*;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.VidaPlus.VitaValore.repository.PlanoRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanosServices {

    @Autowired
    private PlanoRepository planoRepository;
    @Autowired
    private EmpresasRepository empresasRepository;

    public ResponseEntity<?> Atualizar(long id, int idPlano) {
        try {
            Optional<Empresa> empresa = empresasRepository.findById(id);


            Plano planoEscolhido = planoEscolhido(idPlano);


            if (planoEscolhido.getId() == null) {
                planoRepository.save(planoEscolhido);
            }

            if (empresa.isEmpty()) return ResponseEntity.badRequest().body("Empresa inexistente");
            empresa.get().setPlanoAtual(planoEscolhido);
            empresasRepository.save(empresa.get());

            return ResponseEntity.ok(empresa.get());
        } catch (Exception exception) {
            System.out.println("\n" + exception + "\n");
        }
        return ResponseEntity.badRequest().body("Erro em algo");
    }


    @NotNull
    @Contract("_ -> new")
    private Plano planoEscolhido(int idPlano) {
        switch (idPlano) {
            case 1:
                return new PlanoBasico();
            case 2:
                return new PlanoAvancado();
            case 3:
                return new PlanoPremium();
            case 4:
                return new PlanoPersonalizado();
            default:
                return new PlanoFree();
        }
    }
}
