package com.utp.service.impl;

import com.utp.agregates.request.AlumnoRequest;
import com.utp.agregates.response.AlumnoInfoResponse;
import com.utp.entity.*;
import com.utp.repository.*;
import com.utp.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final ApoderadoRepository apoderadoRepository;
    private final FechaPagoRepository fechaPagoRepository;

    @Override
    public AlumnoInfoResponse crearAlumno(AlumnoRequest request) {
        // Verificar que el apoderado existe
        Apoderado apoderado = apoderadoRepository.findById(request.getApoderadoId())
            .orElseThrow(() -> new RuntimeException("Apoderado no encontrado"));

        // Crear documento de identidad
        DocumentoIdentidad documento = DocumentoIdentidad.builder()
            .tipoDocumento(request.getTipoDocumento())
            .numeroDocumento(request.getNumeroDocumento())
            .build();

        // Crear alumno
        Alumno alumno = Alumno.builder()
            .documentoIdentidad(documento)
            .apoderado(apoderado)
            .nombre(request.getNombre())
            .apellido(request.getApellido())
            .fechaNacimiento(request.getFechaNacimiento())
            .genero(request.getGenero())
            .direccion(request.getDireccion())
            .departamento(request.getDepartamento())
            .provincia(request.getProvincia())
            .distrito(request.getDistrito())
            .tieneDiscapacidad(request.getTieneDiscapacidad())
            .diagnosticoMedico(request.getDiagnosticoMedico())
            .estado(true)
            .build();

        alumno = alumnoRepository.save(alumno);
        return convertirAAlumnoInfoResponse(alumno);
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoInfoResponse obtenerAlumnoPorId(Long id) {
        Alumno alumno = alumnoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        return convertirAAlumnoInfoResponse(alumno);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoInfoResponse> listarTodosLosAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        return alumnos.stream()
            .map(this::convertirAAlumnoInfoResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoInfoResponse> listarAlumnosPorApoderado(Long apoderadoId) {
        List<Alumno> alumnos = alumnoRepository.findByApoderadoId(apoderadoId);
        return alumnos.stream()
            .map(this::convertirAAlumnoInfoResponse)
            .collect(Collectors.toList());
    }

    @Override
    public AlumnoInfoResponse actualizarAlumno(Long id, AlumnoRequest request) {
        Alumno alumno = alumnoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        // Actualizar documento si cambió
        alumno.getDocumentoIdentidad().setTipoDocumento(request.getTipoDocumento());
        alumno.getDocumentoIdentidad().setNumeroDocumento(request.getNumeroDocumento());

        // Actualizar datos del alumno
        alumno.setNombre(request.getNombre());
        alumno.setApellido(request.getApellido());
        alumno.setFechaNacimiento(request.getFechaNacimiento());
        alumno.setGenero(request.getGenero());
        alumno.setDireccion(request.getDireccion());
        alumno.setDepartamento(request.getDepartamento());
        alumno.setProvincia(request.getProvincia());
        alumno.setDistrito(request.getDistrito());
        alumno.setTieneDiscapacidad(request.getTieneDiscapacidad());
        alumno.setDiagnosticoMedico(request.getDiagnosticoMedico());

        alumno = alumnoRepository.save(alumno);
        return convertirAAlumnoInfoResponse(alumno);
    }



    @Override
    @Transactional(readOnly = true)
    public List<AlumnoInfoResponse> buscarAlumnosPorNombre(String nombre) {
        List<Alumno> alumnos = alumnoRepository.findByNombreOrApellidoContainingIgnoreCase(nombre);
        return alumnos.stream()
            .map(this::convertirAAlumnoInfoResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoInfoResponse> buscarAlumnosPorNombreCompleto(String nombre, String apellido) {
        List<Alumno> alumnos = alumnoRepository.findByNombreAndApellidoContainingIgnoreCase(nombre, apellido);
        return alumnos.stream()
            .map(this::convertirAAlumnoInfoResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FechaPago> obtenerTodasLasCuotasPorAlumno(Long alumnoId) {
        // Verificar que el alumno existe
        alumnoRepository.findById(alumnoId)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        
        // Obtener todas las cuotas del alumno usando consulta optimizada
        return fechaPagoRepository.findByAlumnoId(alumnoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FechaPago> obtenerCuotasPendientesPorAlumno(Long alumnoId) {
        // Verificar que el alumno existe
        alumnoRepository.findById(alumnoId)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        
        // Obtener solo las cuotas pendientes del alumno usando consulta optimizada
        return fechaPagoRepository.findPendientesByAlumnoId(alumnoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FechaPago> buscarCuotasPorDescripcion(Long alumnoId, String descripcion) {
        // Verificar que el alumno existe
        alumnoRepository.findById(alumnoId)
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        
        // Buscar cuotas por descripción usando consulta optimizada
        return fechaPagoRepository.findByAlumnoIdAndDescripcionContainingIgnoreCase(alumnoId, descripcion);
    }

    private AlumnoInfoResponse convertirAAlumnoInfoResponse(Alumno alumno) {
        return AlumnoInfoResponse.builder()
            .id(alumno.getId())
            .nombre(alumno.getNombre())
            .apellido(alumno.getApellido())
            .numeroDocumento(alumno.getDocumentoIdentidad().getNumeroDocumento())
            .fechaNacimiento(alumno.getFechaNacimiento())
            .genero(alumno.getGenero())
            .direccion(alumno.getDireccion())
            .matriculas(List.of()) // Se llenan desde otro servicio si es necesario
            .horarios(List.of())   // Se llenan desde otro servicio si es necesario
            .build();
    }
}
