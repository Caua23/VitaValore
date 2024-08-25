package com.VidaPlus.VitaValore.controller;

import com.VidaPlus.VitaValore.dto.LoginRequestDto;
import com.VidaPlus.VitaValore.dto.RegisterRequestDto;
import com.VidaPlus.VitaValore.services.EmpresaService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EmpresaService empresaService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@NotNull @RequestBody @Valid LoginRequestDto loginRequest) {

        boolean ValidLogin = empresaService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (!ValidLogin) {
            return ResponseEntity.ok("false");
        }

        return ResponseEntity.ok("login");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@NotNull @RequestBody @Valid RegisterRequestDto RegisterRequest) {
        boolean CreateRegister = empresaService.createRegister(RegisterRequest.getName(), RegisterRequest.getEmail(), RegisterRequest.getCnpj(), RegisterRequest.getPassword());
        if (!CreateRegister) {
            return ResponseEntity.ok("false");
        }
        return ResponseEntity.ok("register");
    }

}
