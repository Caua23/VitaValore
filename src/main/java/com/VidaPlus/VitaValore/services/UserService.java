package com.VidaPlus.VitaValore.services;

import com.VidaPlus.VitaValore.models.Users;
import com.VidaPlus.VitaValore.repository.UserRepository;
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

    public ResponseEntity<?> registerUser(@NotNull String name , String email, String password){
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Campos vazios");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email ja em uso");
        }
        Users newUser = new Users();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
        return ResponseEntity.ok("Usuario cadastrado:\n" + newUser);
    }

    public ResponseEntity<?> loginUser(String email, String password) {
        Optional<Users> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("Email ou senha incorretos");
        }
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return ResponseEntity.badRequest().body("Email ou senha incorretos");
        }
        return ResponseEntity.ok(user.get());
    }

}
