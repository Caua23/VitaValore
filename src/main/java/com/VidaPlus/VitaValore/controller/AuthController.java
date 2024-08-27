package com.VidaPlus.VitaValore.controller;

import com.VidaPlus.VitaValore.dto.CreateResponseDto;
import com.VidaPlus.VitaValore.dto.LoginRequestDto;
import com.VidaPlus.VitaValore.dto.RegisterRequestDto;
import com.VidaPlus.VitaValore.dto.ResponseDto;
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

        ResponseEntity<ResponseDto> ValidLogin = empresaService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (ValidLogin.getStatusCode().is2xxSuccessful()) {
            ResponseDto responseDto = ValidLogin.getBody();
            String nome = responseDto.name();
            String cnpj = responseDto.cnpj();
            String token = responseDto.token();
            return ResponseEntity.ok(new ResponseDto(nome, cnpj, token));
        } else {
            return ResponseEntity.ok("false");
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@NotNull @RequestBody @Valid RegisterRequestDto RegisterRequest) {
        ResponseEntity<CreateResponseDto> CreateRegister = empresaService.createRegister(RegisterRequest.getName(), RegisterRequest.getEmail(), RegisterRequest.getCnpj(), RegisterRequest.getPassword());
        if (CreateRegister.getStatusCode().is2xxSuccessful()) {
            CreateResponseDto CreateResponseDto = CreateRegister.getBody();
            String name = CreateResponseDto.name();
            String cnpj = CreateResponseDto.cnpj();
            String email = CreateResponseDto.email();
            String password = CreateResponseDto.password();
            String token = CreateResponseDto.token();
            return ResponseEntity.ok(new CreateResponseDto(name,cnpj,email,password,token));
        } else {
            return ResponseEntity.ok("false");
        }
    }

}
