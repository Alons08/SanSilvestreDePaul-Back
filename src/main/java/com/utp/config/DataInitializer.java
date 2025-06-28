package com.utp.config;

import com.utp.entity.*;
import com.utp.repository.PersonalRepository;
import com.utp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PersonalRepository personalRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        try {
            // Verificar si ya existe un usuario administrador
            boolean adminExists = userRepository.existsByRole(Role.Administrador);
            if (!adminExists) {
                // Crear documento de identidad para administrador
                DocumentoIdentidad docAdmin = DocumentoIdentidad.builder()
                    .tipoDocumento(TipoDocumento.DNI)
                    .numeroDocumento("70600740")
                    .build();

                User admin = User.builder()
                    .username("Alonso")
                    .password(passwordEncoder.encode("alonso123"))
                    .role(Role.Administrador)
                    .estado(true)
                    .build();
                User savedAdmin = userRepository.save(admin);

                // Crear registro de personal para administrador
                Personal personalAdmin = Personal.builder()
                    .nombre("Alonso Ariam")
                    .apellido("Leandro Quispe")
                    .email("alonso.lq08@gmail.com")
                    .documentoIdentidad(docAdmin)
                    .user(savedAdmin)
                    .estado(true)
                    .build();
                personalRepository.save(personalAdmin);

                log.info("Usuario administrador y personal creado exitosamente: {}", admin.getUsername());
            } else {
                log.info("Usuario administrador ya existe, omitiendo creación");
            }

            // Verificar si ya existe un usuario secretaria
            boolean secretariaExists = userRepository.existsByRole(Role.Secretaria);
            if (!secretariaExists) {
                // Crear documento de identidad para secretaria
                DocumentoIdentidad docSecretaria = DocumentoIdentidad.builder()
                    .tipoDocumento(TipoDocumento.DNI)
                    .numeroDocumento("87654321")
                    .build();

                User secretaria = User.builder()
                    .username("Jose")
                    .password(passwordEncoder.encode("jose123"))
                    .role(Role.Secretaria)
                    .estado(true)
                    .build();
                User savedSecretaria = userRepository.save(secretaria);

                // Crear registro de personal para secretaria
                Personal personalSecretaria = Personal.builder()
                    .nombre("Jose Alberto")
                    .apellido("Hurtado Rivas")
                    .email("programaciondevjose2004@gmail.com")
                    .documentoIdentidad(docSecretaria)
                    .user(savedSecretaria)
                    .estado(true)
                    .build();
                personalRepository.save(personalSecretaria);

                log.info("Usuario secretaria y personal creado exitosamente: {}", secretaria.getUsername());
            } else {
                log.info("Usuario secretaria ya existe, omitiendo creación");
            }

        } catch (Exception e) {
            log.error("Error al inicializar usuarios por defecto: {}", e.getMessage());
        }
    }
}
