package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.models.Empresas;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresasRepository empresasRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean login(String email, String rawPassword) {
        Optional<Empresas> empresas = empresasRepository.findByEmail(email);

        if (empresas.isEmpty()) {
            return false; // Email não encontrado
        }

        Empresas empresa = empresas.get();
        System.out.println("\n Empresa:" + empresa + " Senha:" + empresa.getPassword() + "\n");
        // Retorna true se a senha for igual, e falsa se não, em resumo nos
        // verificamos se existe o email, se não existe cai no if e retorna false
        // se não for vazio o passwordEncoder compara as senhas e ja retorna true ou false
        return passwordEncoder.matches(rawPassword, empresa.getPassword());

    }

    //-----------------------------------Cadastro de empresas-----------------------------------------------

    public boolean createRegister(String name, String email, String cnpj, String password) {
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || cnpj == null || cnpj.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        // Verifica se o CNPJ e o email da empresa já existem
        if (empresasRepository.findBycnpj(cnpj).isPresent() || empresasRepository.findByEmail(email).isPresent()) {
            return false;
        }

        Empresas empresa = new Empresas();
        empresa.setName(name);
        empresa.setEmail(email);
        empresa.setCnpj(cnpj);
        empresa.setPassword(passwordEncoder.encode(password));
        empresasRepository.save(empresa);
        return true;
    }
}
