package com.utp.config;

import com.utp.entity.Role;
import com.utp.entity.User;
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
                User admin = User.builder()
                    .username("Alonso")
                    .password(passwordEncoder.encode("alonso123"))
                    .role(Role.Administrador)
                    .estado(true)
                    .build();
                userRepository.save(admin);
                log.info("Usuario administrador creado exitosamente: {}", admin.getUsername());
            } else {
                log.info("Usuario administrador ya existe, omitiendo creación");
            }

            // Verificar si ya existe un usuario secretaria
            boolean secretariaExists = userRepository.existsByRole(Role.Secretaria);
            if (!secretariaExists) {
                User secretaria = User.builder()
                    .username("Jose")
                    .password(passwordEncoder.encode("jose123"))
                    .role(Role.Secretaria)
                    .estado(true)
                    .build();
                userRepository.save(secretaria);
                log.info("Usuario secretaria creado exitosamente: {}", secretaria.getUsername());
            } else {
                log.info("Usuario secretaria ya existe, omitiendo creación");
            }

        } catch (Exception e) {
            log.error("Error al inicializar usuarios por defecto: {}", e.getMessage());
        }
    }
}
