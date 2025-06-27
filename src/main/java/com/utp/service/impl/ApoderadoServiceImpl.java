package com.utp.service.impl;

import com.utp.agregates.response.*;
import com.utp.entity.*;
import com.utp.repository.*;
import com.utp.service.ApoderadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApoderadoServiceImpl implements ApoderadoService {

    private final ApoderadoRepository apoderadoRepository;
    private final AlumnoRepository alumnoRepository;
    private final FechaPagoRepository fechaPagoRepository;
    private final AulaRepository aulaRepository;
    private final MatriculaRepository matriculaRepository;

    @Override
    public ApoderadoDashboardResponse obtenerDashboardApoderado(Integer userId) {
        Apoderado apoderado = apoderadoRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Apoderado no encontrado"));
        
        List<Alumno> alumnos = alumnoRepository.findByApoderadoUserId(userId);
        
        List<AlumnoInfoResponse> alumnosInfo = alumnos.stream()
            .map(this::convertirAAlumnoInfoResponse)
            .collect(Collectors.toList());
        
        return ApoderadoDashboardResponse.builder()
            .nombreApoderado(apoderado.getNombre())
            .apellidoApoderado(apoderado.getApellido())
            .alumnos(alumnosInfo)
            .build();
    }

    @Override
    public List<FechaPagoResponse> obtenerFechasPagoPendientes(Integer userId) {
        List<FechaPago> fechasPago = fechaPagoRepository.findPendientesByApoderadoUserId(userId);
        
        return fechasPago.stream()
            .map(this::convertirAFechaPagoResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<FechaPagoResponse> obtenerTodasLasFechasPago(Integer userId) {
        List<FechaPago> fechasPago = fechaPagoRepository.findByApoderadoUserId(userId);
        
        return fechasPago.stream()
            .map(this::convertirAFechaPagoResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<FechaPagoResponse> obtenerFechasPagoPagadas(Integer userId) {
        List<FechaPago> fechasPago = fechaPagoRepository.findPagadosByApoderadoUserId(userId);
        
        return fechasPago.stream()
            .map(this::convertirAFechaPagoResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<HorarioResponse> obtenerHorariosAlumnos(Integer userId) {
        List<Aula> aulas = aulaRepository.findAulasByApoderadoUserId(userId);
        
        return aulas.stream()
            .map(this::convertirAHorarioResponse)
            .collect(Collectors.toList());
    }

    private AlumnoInfoResponse convertirAAlumnoInfoResponse(Alumno alumno) {
        List<Matricula> matriculas = matriculaRepository.findByAlumnoId(alumno.getId());
        
        List<MatriculaResponse> matriculasResponse = matriculas.stream()
            .map(this::convertirAMatriculaResponseSimple)
            .collect(Collectors.toList());

        List<HorarioResponse> horarios = matriculas.stream()
            .filter(m -> m.getEstado() == EstadoMatricula.Completada)
            .map(m -> aulaRepository.findByGradoId(m.getGrado().getId()))
            .flatMap(List::stream)
            .map(this::convertirAHorarioResponse)
            .collect(Collectors.toList());

        return AlumnoInfoResponse.builder()
            .id(alumno.getId())
            .nombre(alumno.getNombre())
            .apellido(alumno.getApellido())
            .numeroDocumento(alumno.getDocumentoIdentidad().getNumeroDocumento())
            .fechaNacimiento(alumno.getFechaNacimiento())
            .genero(alumno.getGenero())
            .direccion(alumno.getDireccion())
            .matriculas(matriculasResponse)
            .horarios(horarios)
            .build();
    }

    private MatriculaResponse convertirAMatriculaResponseSimple(Matricula matricula) {
        List<FechaPagoResponse> fechasPago = matricula.getFechasPago() != null ?
            matricula.getFechasPago().stream()
                .map(this::convertirAFechaPagoResponse)
                .collect(Collectors.toList()) : List.of();

        return MatriculaResponse.builder()
            .id(matricula.getId())
            .nombreAlumno(matricula.getAlumno().getNombre())
            .apellidoAlumno(matricula.getAlumno().getApellido())
            .numeroDocumentoAlumno(matricula.getAlumno().getDocumentoIdentidad().getNumeroDocumento())
            .nombreGrado(matricula.getGrado().getNombreGrado())
            .nivelEducativo(matricula.getGrado().getNivel())
            .anoEscolar(matricula.getAnoEscolar())
            .fechaMatricula(matricula.getFechaMatricula())
            .tipoMatricula(matricula.getTipoMatricula())
            .estado(matricula.getEstado())
            .observaciones(matricula.getObservaciones())
            .usuarioMatricula(matricula.getUsuarioMatricula() != null ? 
                matricula.getUsuarioMatricula().getUsername() : null)
            .fechasPago(fechasPago)
            .build();
    }

    private FechaPagoResponse convertirAFechaPagoResponse(FechaPago fechaPago) {
        return FechaPagoResponse.builder()
            .id(fechaPago.getId())
            .descripcion(fechaPago.getDescripcion())
            .fechaVencimiento(fechaPago.getFechaVencimiento())
            .monto(fechaPago.getMonto())
            .fechaPago(fechaPago.getFechaPago())
            .pagado(fechaPago.getPagado())
            .numeroRecibo(fechaPago.getNumeroRecibo())
            .observaciones(fechaPago.getObservaciones())
            .build();
    }

    private HorarioResponse convertirAHorarioResponse(Aula aula) {
        return HorarioResponse.builder()
            .aulaId(aula.getId())
            .nombreAula(aula.getNombre())
            .nombreGrado(aula.getGrado().getNombreGrado())
            .nombreDocente(aula.getDocente().getNombre())
            .apellidoDocente(aula.getDocente().getApellido())
            .horarioInicio(aula.getHorarioInicio())
            .horarioFin(aula.getHorarioFin())
            .capacidad(aula.getCapacidad())
            .build();
    }
}
