package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.dto.CreateResponseDto;
import com.VidaPlus.VitaValore.dto.RegisterRequestDto;
import com.VidaPlus.VitaValore.dto.ResponseDto;
import com.VidaPlus.VitaValore.infra.security.TokenService;
import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresasRepository empresasRepository;
    @Autowired
    private TokenService tokenService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //-----------------------------------Login de empresas-----------------------------------------------
    public ResponseEntity<ResponseDto> login(String email, String rawPassword) {
        Optional<Empresas> empresas = empresasRepository.findByEmail(email);

        if (empresas.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Email não encontrado
        }

        Empresas empresa = empresas.get();
        // Retorna true se a senha for igual, e falsa se não, em resumo nos
        // verificamos se existe o email, se não existe cai no if e retorna false
        // se não for vazio o passwordEncoder compara as senhas e ja retorna true ou false

        String token = tokenService.generateToken(empresa);

        return passwordEncoder.matches(rawPassword, empresa.getPassword())
                ? ResponseEntity.ok(new ResponseDto(empresa.getName(),empresa.getCnpj(), token))
                : ResponseEntity.badRequest().build();

    }

    //-----------------------------------Cadastro de empresas-----------------------------------------------

    public ResponseEntity<CreateResponseDto> createRegister(String name, String email, String cnpj, String password) {
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || cnpj == null || cnpj.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Verifica se o CNPJ e o email da empresa já existem
        if (empresasRepository.findByCnpj(cnpj).isPresent() || empresasRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Empresas empresa = new Empresas();
        empresa.setName(name);
        empresa.setEmail(email);
        empresa.setCnpj(cnpj);
        empresa.setPassword(passwordEncoder.encode(password));
        empresasRepository.save(empresa);
        String token = tokenService.generateToken(empresa);

        return ResponseEntity.ok(new CreateResponseDto(empresa.getName(),empresa.getCnpj(), empresa.getEmail(), empresa.getPassword(), token));

    }


    public ResponseEntity<String> deletarEmpresa(String cnpj){
        Optional<Empresas> empresa = empresasRepository.findByCnpj(cnpj);
        if (empresa.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada para o CNPJ fornecido.");
        }
        empresasRepository.delete(empresa.get());
        return ResponseEntity.ok("Empresa deletada com sucesso.");
    }
}
