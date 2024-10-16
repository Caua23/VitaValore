package com.VidaPlus.VitaValore.infra.security;

import com.VidaPlus.VitaValore.models.enums.Role;
import com.VidaPlus.VitaValore.repository.EmpresasRepository;
import com.VidaPlus.VitaValore.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;


@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    EmpresasRepository empresasRepository;
//    @Autowired
//    AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            SecurityDto securityDto = tokenService.getInfo(token);

            if (securityDto != null) {
                String email = securityDto.getEmail();
                Role role = securityDto.getRole();
                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));


                Object user;
                switch (role) {
                    case EMPRESA -> user = empresasRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
                    case ADMIN -> user = empresasRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("Admin não encontrado"));
                    case USER -> user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                    default -> throw new RuntimeException("Role desconhecido: " + role);
                }

                var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }



    @Nullable
    private String recoverToken(@NotNull HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}