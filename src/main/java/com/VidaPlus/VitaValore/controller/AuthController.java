package com.VidaPlus.VitaValore.controller;

import com.VidaPlus.VitaValore.dto.auth.LoginRequestDto;
import com.VidaPlus.VitaValore.dto.auth.RegisterUpdateRequestDto;
import com.VidaPlus.VitaValore.dto.auth.ResponseDto;
import com.VidaPlus.VitaValore.dto.user.CreateAndUpdateUser;
import com.VidaPlus.VitaValore.services.EmpresaServices;
import com.VidaPlus.VitaValore.services.UserServices;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EmpresaServices empresaService;

    @Autowired
    private UserServices userService;


    //mudar isso no futuro
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@NotNull @RequestBody @Valid LoginRequestDto loginRequest) {

        ResponseEntity<ResponseDto> validLogin = empresaService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (validLogin.getStatusCode().is2xxSuccessful()) {
            ResponseDto responseDto = validLogin.getBody();
            String nome = responseDto.name();
            String cnpj = responseDto.cnpj();
            String token = responseDto.token();
            return ResponseEntity.ok(new ResponseDto(nome, cnpj, token));
        } else if (validLogin.getStatusCode().is4xxClientError()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado, verifique o email e senha ou crie uma conta");
        }
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@NotNull @RequestBody @Valid RegisterUpdateRequestDto RegisterRequest) {
        return empresaService.createRegister(RegisterRequest.getName(),RegisterRequest.getFantasia() , RegisterRequest.getEmail(), RegisterRequest.getCnpj(), RegisterRequest.getPassword());
    }


    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@NotNull @RequestBody @Valid CreateAndUpdateUser userDto) {
        return userService.registerUser(userDto.getName(), userDto.getEmail(), userDto.getPassword());
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser( @NotNull @RequestBody @Valid CreateAndUpdateUser userDto) {
        return userService.loginUser(userDto.getEmail(), userDto.getPassword());
    }

}
