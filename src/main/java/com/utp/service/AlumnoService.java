package com.utp.service;

import com.utp.agregates.request.AlumnoRequest;
import com.utp.agregates.response.AlumnoInfoResponse;
import com.utp.entity.Alumno;
import com.utp.entity.FechaPago;

import java.util.List;

public interface AlumnoService {
    
    AlumnoInfoResponse crearAlumno(AlumnoRequest request);
    
    AlumnoInfoResponse obtenerAlumnoPorId(Long id);
    
    List<AlumnoInfoResponse> listarTodosLosAlumnos();
    
    List<AlumnoInfoResponse> listarAlumnosPorApoderado(Long apoderadoId);
    
    AlumnoInfoResponse actualizarAlumno(Long id, AlumnoRequest request);
    
    List<AlumnoInfoResponse> buscarAlumnosPorNombre(String nombre);
    
    // NUEVAS MEJORAS
    List<AlumnoInfoResponse> buscarAlumnosPorNombreCompleto(String nombre, String apellido);
    
    List<FechaPago> obtenerCuotasPendientesPorAlumno(Long alumnoId);
    
    List<FechaPago> obtenerTodasLasCuotasPorAlumno(Long alumnoId);
    
    List<FechaPago> buscarCuotasPorDescripcion(Long alumnoId, String descripcion);
}
