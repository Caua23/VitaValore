package com.VidaPlus.VitaValore.controller;

import com.VidaPlus.VitaValore.repository.ProdutosRepository;
import com.VidaPlus.VitaValore.services.AdminServices;
import com.VidaPlus.VitaValore.services.ProdutosService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Admins")
public class AdminController {

    @Autowired
    private ProdutosService produtosService;

    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private AdminServices adminServices;
    //http://localhost:3000/Produtos/api/getAllPendingProducts
    @RequestMapping(value = "/Pendentes/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> PendingProducts(@PathVariable("id") @NotNull @Valid Long id) {
        return adminServices.validacaoPendente(id);
    }



    @RequestMapping(value = "/Reprovados/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> ReprovadosProducts(@PathVariable("id") @NotNull @Valid Long id) {
        return adminServices.validacaoReprovado(id);
    }

}
