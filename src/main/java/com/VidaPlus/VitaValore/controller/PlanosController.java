package com.VidaPlus.VitaValore.controller;

import com.VidaPlus.VitaValore.services.PlanosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Planos")
public class PlanosController {

    @Autowired
    private PlanosServices planosServices;

    @RequestMapping(value = "/Atualizar/{Plano}/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> AtualizarPlano(@PathVariable("Plano") int idPlano, @PathVariable("id") Long id) {
        return planosServices.Atualizar(id, idPlano);
    }


}
