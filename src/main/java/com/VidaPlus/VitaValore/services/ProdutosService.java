package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.models.Planos.Plano;
import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.VidaPlus.VitaValore.repository.PlanoRepository;
import com.VidaPlus.VitaValore.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProdutosService {


    @Autowired
    private ProdutosRepository produtosRepository;
    @Autowired
    private EmpresasRepository empresasRepository;

    @Autowired
    private PlanoRepository planoRepository;


    public ResponseEntity<String> CreateProdutos(
            String email,
            String name,
            double preco,
            String imagem,
            String descricao,
            String marca

    ) {

        // Busca a empresa pelo CNPJ
        Optional<Empresas> empresa = empresasRepository.findByEmail(email);
        Optional<Produtos> produtos = produtosRepository.findByDescricaoAndImagem(descricao,imagem);


        if (empresa.isEmpty()) {
            // Se a empresa não for encontrada, retorna false
            return ResponseEntity.badRequest().body("Erro ao criar o produtos");
        }


        Empresas emp = empresa.get();
        Optional<Plano> plano = planoRepository.findById(emp.getPlanoAtual().getId());
        Plano Plano = plano.get();

        if (Plano.getLimite() == emp.getProdutos().size() ) {
            return ResponseEntity.badRequest().body("Limite de produtos excedido");
        }

        if (produtos.isPresent()) {
            return ResponseEntity.badRequest().body("Esse Produto ja existe");
        }

        Produtos newProdutos = new Produtos();
        newProdutos.setName(name);
        newProdutos.setPreco(preco);
        newProdutos.setImagem(imagem);
        newProdutos.setDescricao(descricao);
        newProdutos.setMarca(marca);
        newProdutos.setEmpresa(empresa.get());
        produtosRepository.save(newProdutos);
        return ResponseEntity.ok("Produtos Cadastrados");
    }

    public ResponseEntity<String> deleteProdutos(Long id){
        Optional<Produtos> produtos = produtosRepository.findById(id);

        if(produtos.isEmpty()){
            return ResponseEntity.badRequest().body("Erro ao deletar o produtos");
        }
        produtosRepository.delete(produtos.get());
        return ResponseEntity.ok("Produtos deletado com sucesso");
    }



    public ResponseEntity<String> UpdateProdutos(
            Long id,
            String name,
            double preco,
            String imagem,
            String descricao,
            String marca


    ) {


        Optional<Produtos> produtos = produtosRepository.findById(id);

        if (produtos.isEmpty()) {
            return ResponseEntity.badRequest().body("Erro ao atualizar o produtos");
        }
        Produtos updateProdutos = produtos.get();
        updateProdutos.setName(name);
        updateProdutos.setPreco(preco);
        updateProdutos.setImagem(imagem);
        updateProdutos.setDescricao(descricao);
        updateProdutos.setMarca(marca);
        produtosRepository.save(updateProdutos);
        return ResponseEntity.ok("Produtos Atualizados");
    }


    public List getAllProdutos(){
        return List.of(produtosRepository.findAll());
    }

    public ResponseEntity<Optional> getProdutos(Long id){
        Optional<Produtos> produtos = produtosRepository.findById(id);
        if (produtos.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(produtos);
    }


}
