package com.utp.service.impl;

import com.utp.entity.User;
import com.utp.entity.Apoderado;
import com.utp.entity.Alumno;
import com.utp.entity.Docente;
import com.utp.repository.UserRepository;
import com.utp.repository.ApoderadoRepository;
import com.utp.repository.AlumnoRepository;
import com.utp.repository.DocenteRepository;
import com.utp.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EstadoServiceImpl implements EstadoService {

    private final UserRepository userRepository;
    private final ApoderadoRepository apoderadoRepository;
    private final AlumnoRepository alumnoRepository;
    private final DocenteRepository docenteRepository;

    @Override
    public User cambiarEstadoUsuario(Integer userId, Boolean nuevoEstado) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        user.setEstado(nuevoEstado);
        return userRepository.save(user);
    }

    @Override
    public Apoderado cambiarEstadoApoderado(Long apoderadoId, Boolean nuevoEstado) {
        Apoderado apoderado = apoderadoRepository.findById(apoderadoId)
            .orElseThrow(() -> new RuntimeException("Apoderado no encontrado"));
        
        // Cambiar estado del apoderado
        apoderado.setEstado(nuevoEstado);
        
        // Si se desactiva el apoderado, también desactivar su usuario
        if (!nuevoEstado && apoderado.getUser() != null) {
            apoderado.getUser().setEstado(false);
            userRepository.save(apoderado.getUser());
        }
        
        return apoderadoRepository.save(apoderado);
    }

    @Override
    public Alumno cambiarEstadoAlumno(Long alumnoId, Boolean nuevoEstado) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        
        alumno.setEstado(nuevoEstado);
        return alumnoRepository.save(alumno);
    }

    @Override
    public Docente cambiarEstadoDocente(Long docenteId, Boolean nuevoEstado) {
        Docente docente = docenteRepository.findById(docenteId)
            .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        
        docente.setEstado(nuevoEstado);
        return docenteRepository.save(docente);
    }

    @Override
    public Alumno reasignarApoderado(Long alumnoId, Long nuevoApoderadoId) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        
        Apoderado nuevoApoderado = apoderadoRepository.findById(nuevoApoderadoId)
            .orElseThrow(() -> new RuntimeException("Apoderado no encontrado"));
        
        // Verificar que el nuevo apoderado esté activo
        if (!nuevoApoderado.getEstado()) {
            throw new RuntimeException("No se puede asignar un apoderado inactivo");
        }
        
        // Verificar que el usuario del apoderado esté activo
        if (!nuevoApoderado.getUser().getEstado()) {
            throw new RuntimeException("No se puede asignar un apoderado cuyo usuario está inactivo");
        }
        
        alumno.setApoderado(nuevoApoderado);
        return alumnoRepository.save(alumno);
    }
}
