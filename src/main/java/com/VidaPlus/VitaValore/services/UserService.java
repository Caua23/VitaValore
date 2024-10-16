package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.infra.security.TokenService;
import com.VidaPlus.VitaValore.models.User;
import com.VidaPlus.VitaValore.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> registerUser(@NotNull String name , String email, String password){
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

    public ResponseEntity<?> updateUser(Long id, String name,String email, String password, Integer phone){
        Optional<User> userM = userRepository.findById(id);
        User user = userM.get();
        if (name != null) user.setName(name);
        if (email != null) user.setEmail(email);
        if (password != null) user.setPassword(password);

        if (phone != null) {
            user.setPhone(phone);
        }

        userRepository.save(user);
        String token = tokenService.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<?> getUserToken(@NotBlank String token){

        return ResponseEntity.ok(tokenService.verification(token));
    }

    public ResponseEntity<?> deleteUser(Long id){
        Optional<User> userM = userRepository.findById(id);
        User user = userM.get();
        userRepository.delete(user);

        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

}
