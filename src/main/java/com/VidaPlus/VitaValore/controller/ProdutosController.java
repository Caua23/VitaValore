package com.VidaPlus.VitaValore.controller;


import com.VidaPlus.VitaValore.dto.CreateProdutoDto;
import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.services.ProdutosService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdutosController {

    private ProdutosService ProdutosService;

    @RequestMapping(value = "/Produtos", method =RequestMethod.POST)
    public ResponseEntity<?> createProduto(@NotNull @Valid @RequestBody CreateProdutoDto createProduto) {
        boolean CreateProdutos = ProdutosService.CreateProdutos(createProduto.getEmail(), createProduto.getName(), createProduto.getPreco(), createProduto.getImagem(), createProduto.getDescricao(), createProduto.getMarca());

        if (!CreateProdutos) {
            return ResponseEntity.ok("false");
        }

        return ResponseEntity.ok("Produtos Cadastrados");

    }



    @RequestMapping(value = "/Produtos/Deletar/{id}", method = RequestMethod.DELETE)
    public String deleteProdutos(@Valid @RequestBody long id) {
        return ProdutosService.deleteProdutos(id);
    }
}
