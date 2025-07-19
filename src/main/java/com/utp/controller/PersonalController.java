package com.utp.controller;

import com.utp.dto.request.PersonalRequest;
import com.utp.dto.response.BaseResponse;
import com.utp.dto.response.PersonalResponse;
import com.utp.entity.*;
import com.utp.repository.PersonalRepository;
import com.utp.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/personal")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('Administrador')")
public class PersonalController {

    private final PersonalRepository personalRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<List<PersonalResponse>> listarPersonal() {
        List<Personal> personal = personalRepository.findAll();
        List<PersonalResponse> response = PersonalResponse.fromEntityList(personal);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalResponse> obtenerPersonal(@PathVariable Long id) {
        Optional<Personal> personal = personalRepository.findById(id);
        if (personal.isPresent()) {
            PersonalResponse response = PersonalResponse.fromEntity(personal.get());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BaseResponse> crearPersonal(@Valid @RequestBody PersonalRequest request) {
        try {
            // Verificar si el email ya existe
            if (personalRepository.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest()
                    .body(BaseResponse.error("El email ya está registrado"));
            }

            // Verificar si el nombre de usuario ya existe
            if (userRepository.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest()
                    .body(BaseResponse.error("El nombre de usuario ya está registrado"));
            }

            // Crear documento de identidad
            DocumentoIdentidad documento = DocumentoIdentidad.builder()
                .tipoDocumento(request.getTipoDocumento())
                .numeroDocumento(request.getNumeroDocumento())
                .build();

            // Crear usuario
            User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .estado(true)
                .build();

            User savedUser = userRepository.save(user);

            // Crear personal
            Personal personal = Personal.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .documentoIdentidad(documento)
                .user(savedUser)
                .estado(true)
                .build();

            personalRepository.save(personal);

            return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success("Personal creado exitosamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("Error al crear personal: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> actualizarPersonal(@PathVariable Long id, @Valid @RequestBody PersonalRequest request) {
        try {
            Optional<Personal> personalOpt = personalRepository.findById(id);
            if (personalOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Personal personal = personalOpt.get();

            // Verificar si el email ya existe en otro registro
            Optional<Personal> existingPersonal = personalRepository.findByEmail(request.getEmail());
            if (existingPersonal.isPresent() && !existingPersonal.get().getId().equals(id)) {
                return ResponseEntity.badRequest()
                    .body(BaseResponse.error("El email ya está registrado por otro usuario"));
            }

            // Actualizar datos del personal
            personal.setNombre(request.getNombre());
            personal.setApellido(request.getApellido());
            personal.setEmail(request.getEmail());

            // Actualizar documento de identidad
            DocumentoIdentidad documento = personal.getDocumentoIdentidad();
            documento.setTipoDocumento(request.getTipoDocumento());
            documento.setNumeroDocumento(request.getNumeroDocumento());

            // Actualizar usuario si se proporciona nueva contraseña
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                User user = personal.getUser();
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                userRepository.save(user);
            }

            personalRepository.save(personal);

            return ResponseEntity.ok(BaseResponse.success("Personal actualizado exitosamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("Error al actualizar personal: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<BaseResponse> cambiarEstadoPersonal(@PathVariable Long id, @RequestParam Boolean estado) {
        try {
            Optional<Personal> personalOpt = personalRepository.findById(id);
            if (personalOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Personal personal = personalOpt.get();
            personal.setEstado(estado);
            
            // También actualizar el estado del usuario
            User user = personal.getUser();
            user.setEstado(estado);
            userRepository.save(user);
            
            personalRepository.save(personal);

            String mensaje = estado ? "Personal activado exitosamente" : "Personal desactivado exitosamente";
            return ResponseEntity.ok(BaseResponse.success(mensaje));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("Error al cambiar estado del personal: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> eliminarPersonal(@PathVariable Long id) {
        try {
            Optional<Personal> personalOpt = personalRepository.findById(id);
            if (personalOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            personalRepository.deleteById(id);

            return ResponseEntity.ok(BaseResponse.success("Personal eliminado exitosamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.error("Error al eliminar personal: " + e.getMessage()));
        }
    }
}
