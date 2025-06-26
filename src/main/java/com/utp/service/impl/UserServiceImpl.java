package com.utp.service.impl;

import com.utp.entity.User;
import com.utp.entity.Matricula;
import com.utp.repository.UserRepository;
import com.utp.repository.MatriculaRepository;
import com.utp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MatriculaRepository matriculaRepository;

    @Override
    public Integer obtenerUserIdPorUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return user.getId();
    }

    @Override
    public UserDetails obtenerUserDetailsPorUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    @Override
    public boolean esApoderadoDelAlumno(Integer userId, Long alumnoId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (user.getApoderado() == null) {
            return false;
        }
        
        return user.getApoderado().getAlumnos().stream()
            .anyMatch(alumno -> alumno.getId().equals(alumnoId));
    }

    @Override
    public boolean esMatriculaDelApoderado(Long matriculaId, String username) {
        try {
            Integer userId = obtenerUserIdPorUsername(username);
            Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElse(null);
            
            if (matricula == null) {
                return false;
            }
            
            return matricula.getAlumno().getApoderado().getUser().getId().equals(userId);
        } catch (Exception e) {
            return false;
        }
    }
}
