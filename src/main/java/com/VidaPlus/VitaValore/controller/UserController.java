package com.VidaPlus.VitaValore.controller;

import com.VidaPlus.VitaValore.dto.user.CreateAndUpdateUser;
import com.VidaPlus.VitaValore.infra.security.TokenService;
import com.VidaPlus.VitaValore.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
    public ResponseEntity<?> UpdateUser(@NotNull  @PathVariable("id") @Valid long id , @org.jetbrains.annotations.NotNull @RequestBody @Valid CreateAndUpdateUser updateUser){
        return userService.updateUser(id ,updateUser.getName(), updateUser.getEmail(), updateUser.getPassword(), updateUser.getPhone());
    }
}
