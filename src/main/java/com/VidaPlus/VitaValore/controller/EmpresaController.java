package com.VidaPlus.VitaValore.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpresaController {
    @GetMapping("/empresas")
    public ResponseEntity<?> getEmpresa() {
        return ResponseEntity.ok("SucessoEmpresa!");
    }
}
