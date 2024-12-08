package com.VidaPlus.VitaValore.services;
import com.VidaPlus.VitaValore.models.Comentario;
import com.VidaPlus.VitaValore.models.Produtos;

import com.VidaPlus.VitaValore.models.Empresa;
import com.VidaPlus.VitaValore.models.Planos.Plano;
import com.VidaPlus.VitaValore.models.User;
import com.VidaPlus.VitaValore.models.enums.Status;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.VidaPlus.VitaValore.repository.PlanoRepository;
import com.VidaPlus.VitaValore.repository.ProdutosRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class ProdutosServices {


    @Autowired
    private ProdutosRepository produtosRepository;
    @Autowired
    private EmpresasRepository empresasRepository;
    @Autowired
    private PlanoRepository planoRepository;


    public ResponseEntity<?> CreateProdutos(
            Long idEmpresa,
            String name,
            double preco,
            String imagem,
            String descricao,
            String marca

    ) {

        // Busca a empresa pelo CNPJ
        Optional<Empresa> empresa = empresasRepository.findById(idEmpresa);
        Optional<Produtos> produtos = produtosRepository.findByDescricaoAndImagem(descricao,imagem);


        if (empresa.isEmpty()) {
            // Se a empresa não for encontrada, retorna false
            return ResponseEntity.badRequest().body("Erro ao criar o produtos");
        }


        Empresa emp = empresa.get();
        List<Produtos> produtosGet = emp.getProdutos();
        List<Produtos> produtosAprovados = produtosGet.stream().filter(p -> p.getStatus().equals(Status.APROVADO)).toList();
        List<Produtos> produtosPendentes = produtosGet.stream().filter(p -> p.getStatus().equals(Status.PENDENTE)).toList();
        Optional<Plano> planoGet = planoRepository.findById(emp.getPlanoAtual().getId());
        Plano Plano = planoGet.get();

        if (produtos.isPresent()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Esse produto ja existe.");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(errorResponse);
        }
        if (Plano.getLimite() <= produtosAprovados.size() || Plano.getLimite() <= produtosPendentes.size()) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Limite de produtos excedido.");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(errorResponse);

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

    public ResponseEntity<?> CreateComentario(long id, @NotNull String titulo, String descricao, User users){
        if (titulo.isEmpty() || descricao.isEmpty() || users == null) return ResponseEntity.badRequest().body("Não foi possivel setar esse comentario");
        Optional<Produtos> produtoOpt = produtosRepository.findById(id);

        if (produtoOpt.isEmpty())return ResponseEntity.badRequest().body("Produto Não encontrado");
        Produtos updateComentario = produtoOpt.get();
        Comentario comentario = new Comentario();
        comentario.setTitulo(titulo);
        comentario.setDescricao(descricao);
        comentario.setUser(users);

        updateComentario.setComentario(comentario);
        produtosRepository.save(updateComentario);

        return ResponseEntity.ok().body(comentario);



    }
}
