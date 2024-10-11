package com.VidaPlus.VitaValore.controller;


import com.VidaPlus.VitaValore.dto.auth.RegisterUpdateRequestDto;
import com.VidaPlus.VitaValore.dto.auth.UpdatePassword;
import com.VidaPlus.VitaValore.infra.security.TokenService;
import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.models.enums.Role;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.VidaPlus.VitaValore.services.EmpresaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/Empresa")
public class EmpresaController {
    @Autowired
    private EmpresasRepository empresasRepository;

    @Autowired
    private EmpresaService empresaService;

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

    @GetMapping("/empresasHello")
    public ResponseEntity<?> getEmpresa() {
        return ResponseEntity.ok("HelloEmpresa!");
    }

    @RequestMapping(value = "/Deletar/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEmpresa(@PathVariable("id") @NotNull @Valid Long id){
        Optional<Empresas> empresa = empresasRepository.findById(id);
        if (empresa.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada para o ID fornecido.");
        }
        String cnpj = empresa.get().getCnpj();
        return empresaService.deletarEmpresa(cnpj);

    }

        @RequestMapping(value = "/Atualizar/{id}",method = RequestMethod.PUT)
    public ResponseEntity<String> atualizar(@PathVariable("id") @NotNull @Valid Long id, @RequestBody @Valid RegisterUpdateRequestDto updateRequestDto){
        Optional<Empresas> empresaExistente = empresasRepository.findById(id);
        if (empresaExistente.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada para o ID fornecido.");
        }
        return empresaService.atualizarEmpresa(
                    id,
                    updateRequestDto.getName(),
                    updateRequestDto.getEmail(),
                    updateRequestDto.getCnpj(),
                    updateRequestDto.getPassword()
                );
    }
    @RequestMapping(value = "/Atualizar/Password/{id}",method = RequestMethod.PUT)
    public ResponseEntity<String> atualizarPassword(@PathVariable("id") @NotNull @Valid Long id, @RequestBody @Valid UpdatePassword updateRequestDto){
        Optional<Empresas> empresaExistente = empresasRepository.findById(id);
        if (empresaExistente.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada para o ID fornecido.");
        }
        return empresaService.atualizarSenhaEmpresa(
                id,
                updateRequestDto.getNewPassword(),
                updateRequestDto.getOldPassword()

        );
    }


}
