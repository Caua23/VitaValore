package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.models.Comentario;
import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.models.enums.PerguntasEnum;
import com.VidaPlus.VitaValore.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServices {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public ResponseEntity<?> getComentarios(PerguntasEnum perguntaStatus) {
        List<Comentario> comentarioList = comentarioRepository.findAllByPerguntasEnum(perguntaStatus);
        if (comentarioList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(comentarioList);
    }

    public List<Produtos> getProdutoMaisAvaliado(long id) {
        List<Produtos> produtos = comentarioRepository.findProdutoComMaisAvaliacoes(id, PageRequest.of(0, 1));


        return produtos.isEmpty() ? null : Collections.singletonList(produtos.get(0));
    }
}
