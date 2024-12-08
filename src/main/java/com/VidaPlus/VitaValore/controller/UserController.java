package com.VidaPlus.VitaValore.controller;

import com.VidaPlus.VitaValore.dto.user.CreateAndUpdateUser;
import com.VidaPlus.VitaValore.dto.user.CreateComentario;
import com.VidaPlus.VitaValore.dto.user.CreateCompra;
import com.VidaPlus.VitaValore.infra.security.TokenService;
import com.VidaPlus.VitaValore.services.UserServices;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userService;

    @Autowired
    private TokenService tokenService;



    @RequestMapping(value = "/verification/{token}", method = RequestMethod.POST)
    public ResponseEntity<?> verification(@PathVariable("token") @NotNull @Valid String token) {
        Optional<?> entity = tokenService.verification(token);
        if (entity.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Token inválido ou entidade não encontrada.");
        }

        return ResponseEntity.ok(entity.get());
    }




    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> UpdateUser(@NotNull  @PathVariable("id") @Valid long id , @org.jetbrains.annotations.NotNull @NotNull @RequestBody @Valid CreateAndUpdateUser updateUser){
        return userService.updateUser(id ,updateUser.getName(), updateUser.getEmail(), updateUser.getPassword(), updateUser.getPhone());
    }

    @RequestMapping(value = "/Comprar/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> Comprar(@NotNull @PathVariable("id") @Valid long id, @NotNull @RequestBody @Valid CreateCompra createCompra) {
        return userService.Comprar(id , createCompra.getProdutosId(), createCompra.getQuantidade(), createCompra.getValorPago(), createCompra.getEmpresasId());
    }

    @RequestMapping(value = "/Comentario/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> Comentar(@NotNull @PathVariable("id") @Valid long id, @org.jetbrains.annotations.NotNull @NotNull @RequestBody @Valid CreateComentario comentario) {
        return userService.CreateComentario(id, comentario.getTitulo(), comentario.getDescricao(), comentario.getNotaAvaliacao(), comentario.getProdutosId());
    }

}
