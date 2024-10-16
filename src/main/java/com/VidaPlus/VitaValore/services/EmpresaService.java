package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.dto.auth.ApiResposnsePUT;
import com.VidaPlus.VitaValore.dto.auth.CreateResponseDto;
import com.VidaPlus.VitaValore.dto.auth.ResponseDto;
import com.VidaPlus.VitaValore.infra.security.TokenService;
import com.VidaPlus.VitaValore.models.Empresa;
import com.VidaPlus.VitaValore.models.Planos.Plano;
import com.VidaPlus.VitaValore.models.Planos.PlanoFree;
import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.models.Vendas;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.VidaPlus.VitaValore.repository.PlanoRepository;
import com.VidaPlus.VitaValore.repository.ProdutosRepository;
import com.VidaPlus.VitaValore.repository.VendasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresasRepository empresasRepository;
    @Autowired
    private TokenService tokenService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PlanoRepository planoRepository;
    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private VendasRepository vendaRepository;



    public ResponseEntity<ResponseDto> login(String email, String rawPassword) {
        Optional<Empresa> empresas = empresasRepository.findByEmail(email);

        if (empresas.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Empresa empresa = empresas.get();
        String token = tokenService.generateToken(email, empresa.getRoles());

        return passwordEncoder.matches(rawPassword, empresa.getPassword())
                ? ResponseEntity.ok(new ResponseDto(empresa.getName(),empresa.getCnpj(), token))
                : ResponseEntity.badRequest().build();

    }



    public ResponseEntity<CreateResponseDto> createRegister(String name, String email, String cnpj, String password) {
        if (name == null || name.isEmpty() || email == null || email.isEmpty() || cnpj == null || cnpj.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Verifica se o CNPJ e o email da empresa já existem
        if (empresasRepository.findByCnpj(cnpj).isPresent() || empresasRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Plano planoFree = new PlanoFree();
        planoRepository.save(planoFree);

        Empresa empresa = new Empresa();
        empresa.setName(name);
        empresa.setEmail(email);
        empresa.setCnpj(cnpj);
        empresa.setPassword(passwordEncoder.encode(password));
        empresa.setPlanoAtual(planoFree);
        empresasRepository.save(empresa);
        String token = tokenService.generateToken(email,empresa.getRoles());

        return ResponseEntity.ok(new CreateResponseDto(empresa.getName(),empresa.getCnpj(), empresa.getEmail(), empresa.getPassword(), token));

    }

    //-----------------------------------Deletar empresas-----------------------------------------------
    public ResponseEntity<String> deletarEmpresa(String cnpj){
        Optional<Empresa> empresa = empresasRepository.findByCnpj(cnpj);
        if (empresa.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada para o CNPJ fornecido.");
        }

        List<Produtos> produtos = produtosRepository.findByEmpresaId(empresa.get().getId());

        if (!produtos.isEmpty()) {
            produtosRepository.deleteAll(produtos);
        }

        empresasRepository.delete(empresa.get());
        return ResponseEntity.ok("Empresa deletada com sucesso.");
    }


    //-----------------------------------Atualizar empresas-----------------------------------------------


    public ResponseEntity<?> atualizarEmpresa(
            Long id,
            String name,
            String email,
            String cnpj,
            String password

    ){
        Optional<Empresa> empresa = empresasRepository.findById(id);
        if (empresa.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada para o CNPJ fornecido.");
        }
        empresa.get().setCnpj(cnpj);
        empresa.get().setName(name);
        empresa.get().setEmail(email);
        empresa.get().setPassword(passwordEncoder.encode(password));
        empresasRepository.save(empresa.get());
        String token = tokenService.generateToken(email,empresa.get().getRoles());
        ApiResposnsePUT resposnsePUT = new ApiResposnsePUT("Empresa atualizada com sucesso.", token);
        return ResponseEntity.ok(resposnsePUT);
    }

    public ResponseEntity<String> atualizarSenhaEmpresa(
            Long id,
            String oldPassword,
            String newPassword
    ){
        Optional<Empresa> empresa = empresasRepository.findById(id);

        if (empresa.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada para o Id fornecido.");
        }

        if (!passwordEncoder.matches(oldPassword, empresa.get().getPassword())) {
            return ResponseEntity.badRequest().body("Senha Invalida.");
        }
        empresa.get().setPassword(passwordEncoder.encode(newPassword));
        empresasRepository.save(empresa.get());
        return ResponseEntity.ok("Senha atualizada com sucesso.\n");
    }

    public List<Vendas> getFiveVendas(Long id){

        PageRequest pageRequest = PageRequest.of(0, 5);
        List<Vendas> ultimasVendas = vendaRepository.findUltimasVendasEmpresa(id, pageRequest);


        return ultimasVendas;   
    }
    



}

