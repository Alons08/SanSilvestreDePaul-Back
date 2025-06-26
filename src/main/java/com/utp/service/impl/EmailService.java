package com.utp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    
    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;
    
    public void sendPasswordResetEmail(String toEmail, String token, String apoderadoName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Recuperación de Contraseña - Colegio San Silvestre de Paul");
            
            String content = String.format(
                "Hola %s,\n\n" +
                "Código para cambiar tu contraseña:\n\n" +
                "🔑 %s\n\n" +
                "Ingresa este código en la app para crear tu nueva contraseña.\n" +
                "Válido por 1 hora.\n\n" +
                "Saludos,\n" +
                "Colegio San Silvestre de Paul",
                apoderadoName, token
            );
            
            message.setText(content);
            mailSender.send(message);
            
            log.info("Email de recuperación de contraseña enviado a: {}", toEmail);
        } catch (Exception e) {
            log.error("Error al enviar email de recuperación de contraseña a: {}", toEmail, e);
            throw new RuntimeException("Error al enviar el email de recuperación", e);
        }
    }
}
