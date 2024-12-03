package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.dto.auth.ApiResposnsePUT;
import com.VidaPlus.VitaValore.dto.auth.CreateResponseDto;
import com.VidaPlus.VitaValore.dto.auth.ResponseDto;
import com.VidaPlus.VitaValore.dto.empresa.Empresas;
//import com.VidaPlus.VitaValore.dto.empresa.VendasDoMesDto;
import com.VidaPlus.VitaValore.dto.empresa.VendasDto;
import com.VidaPlus.VitaValore.infra.security.TokenService;
import com.VidaPlus.VitaValore.models.Empresa;
import com.VidaPlus.VitaValore.models.Planos.Plano;
import com.VidaPlus.VitaValore.models.Planos.PlanoFree;
import com.VidaPlus.VitaValore.models.Produtos;
import com.VidaPlus.VitaValore.models.Vendas;
import com.VidaPlus.VitaValore.models.enums.Role;
import com.VidaPlus.VitaValore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private ComentarioRepository comentarioRepository;


    public ResponseEntity<ResponseDto> login(String email, String rawPassword) {
        try {
            Optional<Empresa> empresas = empresasRepository.findByEmail(email);

            if (empresas.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Empresa empresa = empresas.get();
            String token = tokenService.generateToken(email, empresa.getRoles());

            return passwordEncoder.matches(rawPassword, empresa.getPassword()) ? ResponseEntity.ok(new ResponseDto(empresa.getName(), empresa.getCnpj(), token)) : ResponseEntity.badRequest().build();

        }catch (Exception ex){

            return ResponseEntity.badRequest().build();
        }

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
        String token = tokenService.generateToken(email, empresa.getRoles());

        return ResponseEntity.ok(new CreateResponseDto(empresa.getName(), empresa.getCnpj(), empresa.getEmail(), empresa.getPassword(), token));

    }

    //-----------------------------------Deletar empresas-----------------------------------------------
    public ResponseEntity<String> deletarEmpresa(String cnpj) {
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


    public ResponseEntity<?> atualizarEmpresa(Long id, String name, String email, String cnpj, String password

    ) {
        Optional<Empresa> empresa = empresasRepository.findById(id);
        if (empresa.isEmpty()) {
            return ResponseEntity.badRequest().body("Empresa não encontrada para o CNPJ fornecido.");
        }
        empresa.get().setCnpj(cnpj);
        empresa.get().setName(name);
        empresa.get().setEmail(email);
        empresa.get().setPassword(passwordEncoder.encode(password));
        empresasRepository.save(empresa.get());
        String token = tokenService.generateToken(email, empresa.get().getRoles());
        ApiResposnsePUT resposnsePUT = new ApiResposnsePUT("Empresa atualizada com sucesso.", token);
        return ResponseEntity.ok(resposnsePUT);
    }

    public ResponseEntity<String> atualizarSenhaEmpresa(Long id, String oldPassword, String newPassword) {
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

    public List<Vendas> getFiveVendas(Long id) {

        PageRequest pageRequest = PageRequest.of(0, 5);


        return vendaRepository.findUltimasVendasEmpresa(id, pageRequest);
    }


    public List<Object[]> getTopVendas(Long id) {
        PageRequest pageable = PageRequest.of(0, 1);
        List<Object[]> resultado = vendaRepository.findProdutoMaisVendido(id, pageable);

        if (resultado.isEmpty()) return null;

        return resultado;
    }


    public ResponseEntity<?> saque(Long id, double valor) {
        Optional<Empresa> empresasOptional = empresasRepository.findById(id);

        if (empresasOptional.isEmpty()) return ResponseEntity.badRequest().body("Empresa não encontrada.");

        Empresa empresa = empresasOptional.get();
        if (valor > empresa.getWallet()) return ResponseEntity.badRequest().body("Saldo insuficiente.");
        double novoSaldo = empresa.getWallet() - valor;
        empresa.setWallet(novoSaldo);
        empresasRepository.save(empresa);
        return ResponseEntity.ok("Saque realizado com sucesso. \n -" + valor);


    }
//
//    public List<Map<String, Object>> getVendasTresMeses(Long empresaId) {
//        LocalDate dataInicio = LocalDate.now().minusMonths(3);
//
//
//        List<Object[]> vendas = vendaRepository.findVendasUltimosTresMeses(empresaId, dataInicio);
//        List<Object[]> avaliacoes = comentarioRepository.findAvaliacoesUltimosTresMeses(empresaId, dataInicio);
//
//        Map<String, Map<String, Object>> dataMap = new HashMap<>();
//
//
//        for (Object[] venda : vendas) {
//            int ano = (int) venda[0];
//            int mes = (int) venda[1];
//            int dia = (int) venda[2];
//            long totalVendas = ((Number) venda[3]).longValue();
//
//
//            LocalDate data = LocalDate.of(ano, mes, dia);
//            String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//
//            dataMap.putIfAbsent(dataFormatada, new HashMap<>());
//            dataMap.get(dataFormatada).put("Compras", totalVendas);
//
//        }
//
//        for (Object[] avaliacao : avaliacoes) {
//            int ano = (int) avaliacao[0];
//            int mes = (int) avaliacao[1];
//            int dia = (int) avaliacao[2];
//            long totalAvaliacoes = ((Number) avaliacao[2]).longValue();
//            LocalDate data = LocalDate.of(ano, mes, dia);
//            String dataFormatada = data.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//
//            dataMap.putIfAbsent(dataFormatada, new HashMap<>());
//            dataMap.get(dataFormatada).put("Avaliacao", totalAvaliacoes);
//        }
//
//
//        List<Map<String, Object>> resultado = new ArrayList<>();
//        for (Map.Entry<String, Map<String, Object>> entry : dataMap.entrySet()) {
//            Map<String, Object> item = new HashMap<>();
//            item.put("date", entry.getKey());
//            item.putAll(entry.getValue());
//            resultado.add(item);
//        }
//
//        return resultado;
//    }
public List<Map<String, Object>> getVendasTresMeses(Long empresaId) {
    LocalDate dataInicio = LocalDate.now().minusMonths(3);

    List<Object[]> vendas = vendaRepository.findVendasUltimosTresMeses(empresaId, dataInicio);
    List<Object[]> avaliacoes = comentarioRepository.findAvaliacoesUltimosTresMeses(empresaId, dataInicio);

    Map<String, Map<String, Object>> dataMap = new HashMap<>();

    // Processando vendas
    for (Object[] venda : vendas) {
        int ano = (int) venda[0];
        int mes = (int) venda[1];
        int dia = (int) venda[2];
        long totalVendas = ((Number) venda[3]).longValue();

        LocalDate data = LocalDate.of(ano, mes, dia);
        String dataFormatada = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        dataMap.putIfAbsent(dataFormatada, new HashMap<>());
        dataMap.get(dataFormatada).put("Compras", totalVendas);
    }

    // Processando avaliações
    for (Object[] avaliacao : avaliacoes) {
        int ano = (int) avaliacao[0];
        int mes = (int) avaliacao[1];
        int dia = (int) avaliacao[2];
        long totalAvaliacoes = ((Number) avaliacao[2]).longValue();

        LocalDate data = LocalDate.of(ano, mes, dia);
        String dataFormatada = data.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        dataMap.putIfAbsent(dataFormatada, new HashMap<>());
        dataMap.get(dataFormatada).put("Avaliacao", totalAvaliacoes);
    }


    List<Map<String, Object>> resultado = new ArrayList<>();
    for (Map.Entry<String, Map<String, Object>> entry : dataMap.entrySet()) {
        Map<String, Object> item = new HashMap<>();
        item.put("date", entry.getKey());


        long compras = (long) entry.getValue().getOrDefault("Compras", 0L);
        long avaliacao = (long) entry.getValue().getOrDefault("Avaliacao", 0L);

        item.put("Compras", compras);
        item.put("Avaliacao", avaliacao);
        resultado.add(item);
    }

    return resultado;
}

}

