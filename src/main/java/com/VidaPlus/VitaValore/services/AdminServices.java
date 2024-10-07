package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.models.enums.Status;
import com.VidaPlus.VitaValore.repository.ProdutosRepository;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@Service
public class AdminServices {

    @Autowired
    private ProdutosService produtosService;

    @Autowired
    private ProdutosRepository produtosRepository;



    public ResponseEntity<?> validacaoPendente(Long id) {
        Optional<Produtos> produto = produtosRepository.findById(id);

        if (produto.isEmpty()) {
            return ResponseEntity.badRequest().body("Erro ao validar o pendente");
        }

        Produtos updateProdutos = produto.get();
        updateProdutos.setStatus(Status.APROVADO);
        produtosRepository.save(updateProdutos);
        return ResponseEntity.ok("Validado com sucesso\n" + updateProdutos);
    }

    public ResponseEntity<?> validacaoReprovado(Long id) {
        Optional<Produtos> produtos = produtosRepository.findById(id);
        if (produtos.isEmpty()) {
            return ResponseEntity.badRequest().body("Erro ao validar o pendente");
        }
        Produtos updateProdutos = produtos.get();
        updateProdutos.setStatus(Status.REJEITADO);
        produtosRepository.save(updateProdutos);
        return ResponseEntity.ok("Produto Rejeitado\n" + updateProdutos);
    }

}
