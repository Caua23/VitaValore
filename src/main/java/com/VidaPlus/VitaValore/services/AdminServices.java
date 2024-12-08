package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.models.enums.Status;
import com.VidaPlus.VitaValore.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AdminServices {

    @Autowired
    private ProdutosServices produtosService;

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
