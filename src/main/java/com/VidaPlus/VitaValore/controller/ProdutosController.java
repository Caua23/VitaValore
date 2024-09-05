package com.VidaPlus.VitaValore.controller;


import com.VidaPlus.VitaValore.dto.produtos.CreateProdutoDto;
import com.VidaPlus.VitaValore.services.ProdutosService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Produtos")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;

    @RequestMapping(value = "/Cadastrar", method = RequestMethod.POST)
    public ResponseEntity<String> createProduto(@NotNull @Valid @RequestBody CreateProdutoDto createProduto) {
        return produtosService.CreateProdutos(
                createProduto.getEmail(),
                createProduto.getName(),
                createProduto.getPreco(),
                createProduto.getImagem(),
                createProduto.getDescricao(),
                createProduto.getMarca()
        );

    }



    @RequestMapping(value = "/Deletar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProdutos(@PathVariable("id") @NotNull @Valid Long id) {
        return produtosService.deleteProdutos(id);
    }
}
