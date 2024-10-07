package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.models.Planos.Plano;
import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.models.enums.Status;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.VidaPlus.VitaValore.repository.PlanoRepository;
import com.VidaPlus.VitaValore.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        List<Produtos> produtosGet = emp.getProdutos();
        List<Produtos> produtosAprovados = produtosGet.stream().filter(p -> p.getStatus().equals(Status.APROVADO)).toList();
        List<Produtos> produtosPendentes = produtosGet.stream().filter(p -> p.getStatus().equals(Status.PENDENTE)).toList();
        Optional<Plano> planoGet = planoRepository.findById(emp.getPlanoAtual().getId());
        Plano Plano = planoGet.get();

        if (produtos.isPresent()) {
            return ResponseEntity.badRequest().body("Esse Produto ja existe");
        }
        if (Plano.getLimite() <= produtosAprovados.size() || Plano.getLimite() <= produtosPendentes.size()) {
            return ResponseEntity.badRequest().body("Limite de produtos excedido");
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


    public List<Produtos> getAllProdutos(Status status){
        return produtosRepository.findByStatus(status);
    }

    public ResponseEntity<Optional> getProdutos(Long id){
        Optional<Produtos> produtos = produtosRepository.findById(id);
        if (produtos.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(produtos);
    }

    public ResponseEntity<?> getAllProductsEmpresa(long id){

        List<Produtos> produtos = produtosRepository.findByEmpresaId(id);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT  ).body("Sua empresa não tem Produtos");
        }
        return ResponseEntity.ok().body(produtos);

    }

}
