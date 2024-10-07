package com.VidaPlus.VitaValore.controller;

import com.VidaPlus.VitaValore.dto.user.UpdateUser;
import com.VidaPlus.VitaValore.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> UpdateUser(@NotNull  @PathVariable("id") @Valid long id , @RequestBody @Valid UpdateUser updateUser){
        return userService.updateUser(id ,updateUser.getName(), updateUser.getEmail(), updateUser.getPassword(), updateUser.getPhone());
    }
}
