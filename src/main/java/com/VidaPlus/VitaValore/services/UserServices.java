package com.VidaPlus.VitaValore.services;

//import com.VidaPlus.VitaValore.dto.empresa.VendasDoMesDto;
import com.VidaPlus.VitaValore.infra.security.TokenService;
import com.VidaPlus.VitaValore.models.*;
import com.VidaPlus.VitaValore.models.enums.StatusPagamento;
import com.VidaPlus.VitaValore.repository.*;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private TokenService tokenService;

    @Autowired
    private VendasRepository vendasRepository;
    @Autowired
    private EmpresasRepository empresasRepository;
    @Autowired
    private ProdutosRepository produtosRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    public ResponseEntity<?> registerUser(@NotNull String name, String email, String password) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Campos vazios");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email ja em uso");
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
        String token = tokenService.generateToken(email, newUser.getRole());
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<?> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Email ou senha incorretos");
        }
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return ResponseEntity.badRequest().body("Email ou senha incorretos");
        }
        String token = tokenService.generateToken(email, user.get().getRole());
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<?> updateUser(Long id, String name, String email, String password, String phone) {
        Optional<User> userM = userRepository.findById(id);
        User user = userM.get();
        if (name != null) user.setName(name);
        if (email != null) user.setEmail(email);
        if (password != null) user.setPassword(password);

        if (phone != null) {
            user.setPhone(String.valueOf(phone));
        }

        userRepository.save(user);
        String token = tokenService.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<?> getUserToken(@NotBlank String token) {

        return ResponseEntity.ok(tokenService.verification(token));
    }

    public ResponseEntity<?> deleteUser(Long id) {
        Optional<User> userM = userRepository.findById(id);
        User user = userM.get();
        userRepository.delete(user);

        return ResponseEntity.ok("Usuário deletado com sucesso");
    }


    public ResponseEntity<?> Comprar(long id, long produtosId, int quantidade, double valorPago, long empresaId) {
//        Optional<User> userOptional = userRepository.findById(id);
        Optional<Empresa> empresaOptional = empresasRepository.findById(empresaId);
        Optional<Produtos> produtosOptional = produtosRepository.findById(produtosId);
//        if (userOptional.isEmpty() || empresaOptional.isEmpty() || produtosOptional.isEmpty()) {
//            return ResponseEntity.badRequest().body("Erro ao criar compra");
//        }
        if (  produtosOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Produto não encontrado");
       }
        if ( empresaOptional.isEmpty() ) {
            return ResponseEntity.badRequest().body("Empresa não encontrado");
        }
//        User user = userOptional.get();
        Empresa empresa = empresaOptional.get();
        Produtos produtos = produtosOptional.get();
        //-
        Vendas vendas = new Vendas();
        //-
        vendas.setProdutos(produtos);
        vendas.setQuantidade(quantidade);
//        vendas.setDataVenda(LocalDate.now());
        vendas.setDataVenda(Vendas.gerarDataAleatoria(2024, 2024));
        vendas.setStatusPagamento(StatusPagamento.APROVADO);
        vendas.setValorPago(valorPago);
//        vendas.setComprador(user);
        //-
        double newWallet = empresa.getWallet() + (valorPago * quantidade);
        empresa.setWallet(newWallet);
        //-
        vendas.setEmpresas(empresa);
        //-
        empresasRepository.save(empresa);
        vendasRepository.save(vendas);
        return ResponseEntity.ok(vendas);
    }


    public ResponseEntity<?> CreateComentario(Long id, String titulo, String descricao, int notaAvaliacao, long produtosId) {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<Produtos> produtosOptional = produtosRepository.findById(produtosId);
        if (userOptional.isEmpty() || produtosOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Erro ao criar comentario");
        }
        User user = userOptional.get();
        Produtos produtos = produtosOptional.get();

        Comentario comentario = new Comentario();

        comentario.setTitulo(titulo);
        comentario.setDescricao(descricao);

        double somaNotas = comentarioRepository.findByAvaliacoes(produtos.getEmpresa().get().getId());
        double notaVerificacao =  ((somaNotas + notaAvaliacao) / (somaNotas + 1));
        System.out.println(somaNotas);
        System.out.println(notaVerificacao);

        comentario.setNotaAvaliacao(notaVerificacao);


        comentario.setUser(user);
        comentario.setDataComentario(LocalDate.now());
        comentario.setProduto(produtos);

        comentarioRepository.save(comentario);

        return ResponseEntity.ok(comentario);

    }







}
