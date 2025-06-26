package com.utp.controller;

import com.utp.agregates.request.ForgotPasswordRequest;
import com.utp.agregates.request.LoginRequest;
import com.utp.agregates.request.RegisterRequest;
import com.utp.agregates.request.ResetPasswordRequest;
import com.utp.agregates.response.AuthResponse;
import com.utp.agregates.response.BaseResponse;
import com.utp.service.AuthService;
import com.utp.service.impl.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        response.setSuccess(true);
        response.setMessage("Login exitoso");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        response.setSuccess(true);
        response.setMessage("Registro exitoso");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<BaseResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            passwordResetService.initiatePasswordReset(request.getEmail());
            return ResponseEntity.ok(BaseResponse.success(
                "Si el email está registrado, recibirás las instrucciones para restablecer tu contraseña"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error("Error al procesar la solicitud"));
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<BaseResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            boolean success = passwordResetService.resetPassword(
                request.getToken(), 
                request.getNewPassword(), 
                request.getConfirmPassword()
            );
            
            if (success) {
                return ResponseEntity.ok(BaseResponse.success("Contraseña restablecida exitosamente"));
            } else {
                return ResponseEntity.badRequest().body(BaseResponse.error("Token inválido o expirado"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error("Error al restablecer la contraseña"));
        }
    }
}