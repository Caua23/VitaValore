package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.VidaPlus.VitaValore.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProdutosService {


    @Autowired
    private ProdutosRepository produtosRepository;
    @Autowired
    private EmpresasRepository empresasRepository;

    public boolean CreateProdutos(
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
            return false;
        }
        if (produtos.isPresent()) {
            return false;
        }
        Produtos newProdutos = new Produtos();
        newProdutos.setName(name);
        newProdutos.setPreco(preco);
        newProdutos.setImagem(imagem);
        newProdutos.setDescricao(descricao);
        newProdutos.setMarca(marca);
        newProdutos.setEmpresa(empresa.get());
        produtosRepository.save(newProdutos);
        return true;
    }

    public String deleteProdutos(Long id){
        Optional<Produtos> produtos = produtosRepository.findById(id);

        if(produtos.isEmpty()){
            return "Erro ao deletar o produtos";
        }
        produtosRepository.delete(produtos.get());
        return "Produtos deletado com sucesso";
    }

}
