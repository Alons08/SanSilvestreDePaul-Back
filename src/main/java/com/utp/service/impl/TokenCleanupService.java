package com.utp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupService {
    
    private final PasswordResetService passwordResetService;
    
    // Ejecutar cada hora para limpiar tokens expirados
    @Scheduled(fixedRate = 3600000) // 1 hora en milisegundos
    public void cleanupExpiredTokens() {
        try {
            passwordResetService.cleanupExpiredTokens();
        } catch (Exception e) {
            log.error("Error durante la limpieza de tokens expirados", e);
        }
    }
}
