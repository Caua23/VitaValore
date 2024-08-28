package com.VidaPlus.VitaValore.controller;


import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.VidaPlus.VitaValore.services.EmpresaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class EmpresaController {
    @Autowired
    private EmpresasRepository empresasRepository;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/empresasHello")
    public ResponseEntity<?> getEmpresa() {
        return ResponseEntity.ok("SucessoEmpresa!");
    }
    @RequestMapping(value = "/Deletar/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEmpresa(@PathVariable("id") @NotNull @Valid Long id){
        Optional<Empresas> empresa = empresasRepository.findById(id);
        if (empresa.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada para o ID fornecido.");
        }
        String cnpj = empresa.get().getCnpj();
        ResponseEntity<String> response = empresaService.deletarEmpresa(cnpj);
        return response;

    }

}
