package com.VidaPlus.VitaValore.controller;


import com.VidaPlus.VitaValore.dto.produtos.CreateProdutoDto;
import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.repository.ProdutosRepository;
import com.VidaPlus.VitaValore.services.ProdutosService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "${URL_FRONTEND}", maxAge = 3600)
@RestController
@RequestMapping("/Produtos")
public class ProdutosController {

    @Autowired
    private ProdutosService produtosService;
    @Autowired
    private ProdutosRepository produtosRepository;

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


    @RequestMapping(value = "/Atualizar/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateProdutos(@PathVariable("id") @NotNull @Valid Long id, @RequestBody CreateProdutoDto createProduto) {
        Optional<Produtos> produtos = produtosRepository.findById(id);
        if (produtos.isEmpty()) {
            return ResponseEntity.badRequest().body("Produto não existente.");
        }

        return produtosService.UpdateProdutos(
                id,
                createProduto.getName(),
                createProduto.getPreco(),
                createProduto.getImagem(),
                createProduto.getDescricao(),
                createProduto.getMarca()

        );
    }

    @RequestMapping(value = "/api/getProdutos/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional> getProdutos(@PathVariable("id") @NotNull @Valid Long id) {
        return produtosService.getProdutos(id);
    }


        @RequestMapping(value = "/api/getAllProdutos", method = RequestMethod.GET)
    public ResponseEntity<List> getAllProdutos() {
        return ResponseEntity.ok().body(produtosService.getAllProdutos());
    }

    @RequestMapping(value = "/Deletar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProdutos(@PathVariable("id") @NotNull @Valid Long id) {
        return produtosService.deleteProdutos(id);
    }
}
