package com.utp.service.impl;

import com.utp.agregates.request.LoginRequest;
import com.utp.agregates.request.RegisterRequest;
import com.utp.agregates.response.AuthResponse;
import com.utp.config.JwtService;
import com.utp.entity.Role;
import com.utp.entity.User;
import com.utp.repository.UserRepository;

import com.utp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserDetails user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciales inválidas");
        }
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        // Validación adicional manual (opcional)
        if (request.getPassword() == null || request.getPassword().trim().length() < 8 || request.getPassword().trim().length() > 15) {
            throw new RuntimeException("La contraseña debe tener entre 8 y 15 caracteres");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        User user = User.builder()
                .username(request.getUsername().trim())
                .password(passwordEncoder.encode(request.getPassword().trim()))
                .role(request.getRole() != null ? request.getRole() : Role.Apoderado)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}