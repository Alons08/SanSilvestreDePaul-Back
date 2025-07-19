package com.utp.service;

import com.utp.dto.request.MatriculaRequest;
import com.utp.dto.response.MatriculaResponse;
import com.utp.entity.EstadoMatricula;

import java.time.Year;
import java.util.List;

public interface MatriculaService {
    
    // Crear matrícula nueva (solo secretaria)
    MatriculaResponse crearMatriculaNueva(MatriculaRequest request, Integer userId);
    
    // Crear matrícula de ratificación (solo apoderado)
    MatriculaResponse crearMatriculaRatificacion(MatriculaRequest request, Integer userId);
    
    // Obtener matrícula por ID
    MatriculaResponse obtenerMatriculaPorId(Long id);
    
    // Listar matrículas por apoderado
    List<MatriculaResponse> listarMatriculasPorApoderado(Integer userId);
    
    // Listar todas las matrículas (solo secretaria/admin)
    List<MatriculaResponse> listarTodasLasMatriculas();
    
    // Actualizar estado de matrícula
    MatriculaResponse actualizarEstadoMatricula(Long id, EstadoMatricula nuevoEstado);
    
    // Obtener matrículas por estado
    List<MatriculaResponse> obtenerMatriculasPorEstado(EstadoMatricula estado);
    
    // Obtener matrículas por año escolar
    List<MatriculaResponse> obtenerMatriculasPorAnoEscolar(Year anoEscolar);
    
    // Verificar si alumno puede ser matriculado
    boolean puedeMatricular(Long alumnoId, Year anoEscolar);
    
    // Eliminar matrícula (solo si está pendiente)
    void eliminarMatricula(Long id, Integer userId);
}
