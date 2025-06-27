package com.utp.service;

import com.utp.entity.User;
import com.utp.entity.Apoderado;
import com.utp.entity.Alumno;
import com.utp.entity.Docente;

public interface EstadoService {
    
    // Cambiar estado de usuario
    User cambiarEstadoUsuario(Integer userId, Boolean nuevoEstado);
    
    // Cambiar estado de apoderado (también afecta al usuario)
    Apoderado cambiarEstadoApoderado(Long apoderadoId, Boolean nuevoEstado);
    
    // Cambiar estado de alumno
    Alumno cambiarEstadoAlumno(Long alumnoId, Boolean nuevoEstado);
    
    // Cambiar estado de docente
    Docente cambiarEstadoDocente(Long docenteId, Boolean nuevoEstado);
    
    // Reasignar alumno a otro apoderado activo
    Alumno reasignarApoderado(Long alumnoId, Long nuevoApoderadoId);
}
