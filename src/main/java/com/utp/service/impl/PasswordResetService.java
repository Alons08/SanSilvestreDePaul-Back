package com.utp.service.impl;

import com.utp.entity.Apoderado;
import com.utp.entity.Personal;
import com.utp.entity.PasswordResetToken;
import com.utp.entity.User;
import com.utp.repository.ApoderadoRepository;
import com.utp.repository.PersonalRepository;
import com.utp.repository.PasswordResetTokenRepository;
import com.utp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetService {
    
    private final ApoderadoRepository apoderadoRepository;
    private final PersonalRepository personalRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TOKEN_LENGTH = 32;
    
    @Transactional
    public void initiatePasswordReset(String email) {
        User user = null;
        String userName = null;
        
        // Buscar primero en apoderados
        Optional<Apoderado> apoderadoOpt = apoderadoRepository.findByEmail(email);
        if (apoderadoOpt.isPresent()) {
            Apoderado apoderado = apoderadoOpt.get();
            user = apoderado.getUser();
            userName = apoderado.getNombre() + " " + apoderado.getApellido();
        } else {
            // Si no se encuentra en apoderados, buscar en personal
            Optional<Personal> personalOpt = personalRepository.findByEmail(email);
            if (personalOpt.isPresent()) {
                Personal personal = personalOpt.get();
                user = personal.getUser();
                userName = personal.getNombre() + " " + personal.getApellido();
            }
        }
        
        if (user == null) {
            // Lanzar excepción cuando el email no existe
            log.warn("Intento de recuperación de contraseña para email no registrado: {}", email);
            throw new IllegalArgumentException("El email no está registrado en el sistema");
        }
        
        // Eliminar tokens existentes para este email
        tokenRepository.deleteByEmail(email);
        
        // Generar nuevo token
        String token = generateSecureToken();
        
        // Crear y guardar el token
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .email(email)
                .user(user)
                .build();
        
        tokenRepository.save(resetToken);
        
        // Enviar email
        emailService.sendPasswordResetEmail(email, token, userName);
        
        log.info("Token de recuperación de contraseña generado para: {}", email);
    }
    
    @Transactional
    public boolean resetPassword(String token, String newPassword, String confirmPassword) {
        // Validar que las contraseñas coincidan
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        
        // Buscar el token
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByTokenAndUsedFalse(token);
        
        if (tokenOpt.isEmpty()) {
            log.warn("Intento de usar token inválido o ya usado: {}", token);
            return false;
        }
        
        PasswordResetToken resetToken = tokenOpt.get();
        
        // Verificar si el token ha expirado
        if (resetToken.isExpired()) {
            log.warn("Intento de usar token expirado: {}", token);
            tokenRepository.delete(resetToken);
            return false;
        }
        
        // Actualizar la contraseña del usuario
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        // Marcar el token como usado
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
        
        log.info("Contraseña restablecida exitosamente para usuario: {}", user.getUsername());
        return true;
    }
    
    @Transactional
    public void cleanupExpiredTokens() {
        tokenRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        log.info("Tokens expirados eliminados");
    }
    
    private String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);
        
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        
        return token.toString();
    }
}
