package com.utp.service.impl;

import com.utp.agregates.request.MatriculaRequest;
import com.utp.agregates.response.MatriculaResponse;
import com.utp.agregates.response.FechaPagoResponse;
import com.utp.entity.*;
import com.utp.repository.*;
import com.utp.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MatriculaServiceImpl implements MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlumnoRepository alumnoRepository;
    private final GradoRepository gradoRepository;
    private final UserRepository userRepository;
    private final FechaPagoRepository fechaPagoRepository;

    @Override
    public MatriculaResponse crearMatriculaNueva(MatriculaRequest request, Integer userId) {
        // Verificar que el usuario sea secretaria o administrador
        User usuario = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!esUsuarioAutorizado(usuario, TipoMatricula.Nueva)) {
            throw new RuntimeException("No tiene permisos para crear matrículas nuevas");
        }
        
        return crearMatricula(request, usuario);
    }

    @Override
    public MatriculaResponse crearMatriculaRatificacion(MatriculaRequest request, Integer userId) {
        // Verificar que el usuario sea apoderado
        User usuario = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!esUsuarioAutorizado(usuario, TipoMatricula.Ratificacion)) {
            throw new RuntimeException("No tiene permisos para crear matrículas de ratificación");
        }
        
        // Verificar que el alumno pertenezca al apoderado
        Alumno alumno = alumnoRepository.findById(request.getAlumnoId())
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        
        if (!alumno.getApoderado().getUser().getId().equals(userId)) {
            throw new RuntimeException("No puede matricular a un alumno que no está a su cargo");
        }
        
        return crearMatricula(request, usuario);
    }

    private MatriculaResponse crearMatricula(MatriculaRequest request, User usuario) {
        // Validar que el alumno no esté ya matriculado para el año escolar
        if (!puedeMatricular(request.getAlumnoId(), request.getAnoEscolar())) {
            throw new RuntimeException("El alumno ya está matriculado para este año escolar");
        }
        
        Alumno alumno = alumnoRepository.findById(request.getAlumnoId())
            .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        
        Grado grado = gradoRepository.findById(request.getGradoId())
            .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        
        // Crear la matrícula
        Matricula matricula = Matricula.builder()
            .alumno(alumno)
            .grado(grado)
            .anoEscolar(request.getAnoEscolar())
            .fechaMatricula(LocalDate.now())
            .tipoMatricula(request.getTipoMatricula())
            .usuarioMatricula(usuario)
            .observaciones(request.getObservaciones())
            .estado(EstadoMatricula.Pendiente)
            .build();
        
        matricula = matriculaRepository.save(matricula);
        
        // Crear fechas de pago automáticamente
        crearFechasPagoAutomaticas(matricula);
        
        // Recargar la matrícula con las fechas de pago
        matricula = matriculaRepository.findById(matricula.getId())
            .orElseThrow(() -> new RuntimeException("Error al recargar matrícula"));
        
        return convertirAMatriculaResponse(matricula);
    }

    private void crearFechasPagoAutomaticas(Matricula matricula) {
        // Crear 10 cuotas mensuales (marzo a diciembre)
        BigDecimal montoCuota = new BigDecimal("150.00"); // Monto ejemplo
        
        for (int mes = 3; mes <= 12; mes++) {
            FechaPago fechaPago = FechaPago.builder()
                .matricula(matricula)
                .descripcion("Cuota " + mes + "/" + matricula.getAnoEscolar())
                .fechaVencimiento(LocalDate.of(matricula.getAnoEscolar().getValue(), mes, 15))
                .monto(montoCuota)
                .pagado(false)
                .build();
            
            fechaPagoRepository.save(fechaPago);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MatriculaResponse obtenerMatriculaPorId(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
        
        return convertirAMatriculaResponse(matricula);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatriculaResponse> listarMatriculasPorApoderado(Integer userId) {
        List<Matricula> matriculas = matriculaRepository.findByApoderadoUserId(userId);
        return matriculas.stream()
            .map(this::convertirAMatriculaResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatriculaResponse> listarTodasLasMatriculas() {
        List<Matricula> matriculas = matriculaRepository.findAll();
        return matriculas.stream()
            .map(this::convertirAMatriculaResponse)
            .collect(Collectors.toList());
    }

    @Override
    public MatriculaResponse actualizarEstadoMatricula(Long id, EstadoMatricula nuevoEstado) {
        Matricula matricula = matriculaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
        
        matricula.setEstado(nuevoEstado);
        matricula = matriculaRepository.save(matricula);
        
        return convertirAMatriculaResponse(matricula);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatriculaResponse> obtenerMatriculasPorEstado(EstadoMatricula estado) {
        List<Matricula> matriculas = matriculaRepository.findByEstado(estado);
        return matriculas.stream()
            .map(this::convertirAMatriculaResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatriculaResponse> obtenerMatriculasPorAnoEscolar(Year anoEscolar) {
        List<Matricula> matriculas = matriculaRepository.findByAnoEscolar(anoEscolar);
        return matriculas.stream()
            .map(this::convertirAMatriculaResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean puedeMatricular(Long alumnoId, Year anoEscolar) {
        return matriculaRepository.findByAlumnoIdAndAnoEscolar(alumnoId, anoEscolar).isEmpty();
    }

    @Override
    public void eliminarMatricula(Long id, Integer userId) {
        Matricula matricula = matriculaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Matrícula no encontrada"));
        
        if (!matricula.getEstado().equals(EstadoMatricula.Pendiente)) {
            throw new RuntimeException("Solo se pueden eliminar matrículas pendientes");
        }
        
        // Verificar permisos del usuario
        User usuario = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (usuario.getRole() == Role.Apoderado) {
            if (!matricula.getAlumno().getApoderado().getUser().getId().equals(userId)) {
                throw new RuntimeException("No puede eliminar una matrícula que no le pertenece");
            }
        }
        
        matriculaRepository.delete(matricula);
    }

    private boolean esUsuarioAutorizado(User usuario, TipoMatricula tipoMatricula) {
        if (tipoMatricula == TipoMatricula.Nueva) {
            return usuario.getRole() == Role.Administrador || usuario.getRole() == Role.Secretaria;
        } else if (tipoMatricula == TipoMatricula.Ratificacion) {
            return usuario.getRole() == Role.Apoderado;
        }
        return false;
    }

    private MatriculaResponse convertirAMatriculaResponse(Matricula matricula) {
        // Cargar fechas de pago directamente desde el repositorio
        List<FechaPago> fechasPagoEntity = fechaPagoRepository.findByMatriculaId(matricula.getId());
        
        List<FechaPagoResponse> fechasPago = fechasPagoEntity.stream()
            .map(this::convertirAFechaPagoResponse)
            .collect(Collectors.toList());

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
            .usuarioMatricula(matricula.getUsuarioMatricula().getUsername())
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
}
