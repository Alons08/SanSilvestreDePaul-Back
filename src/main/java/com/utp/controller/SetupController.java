package com.utp.controller;

import com.utp.agregates.request.*;
import com.utp.agregates.response.*;
import com.utp.entity.*;
import com.utp.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/setup")
@RequiredArgsConstructor
public class SetupController {

    private final ApoderadoRepository apoderadoRepository;
    private final AlumnoRepository alumnoRepository;
    private final DocenteRepository docenteRepository;
    private final GradoRepository gradoRepository;
    private final AulaRepository aulaRepository;
    private final UserRepository userRepository;
    private final FechaPagoRepository fechaPagoRepository;
    private final CursoRepository cursoRepository;
    private final GradoCursoRepository gradoCursoRepository;
    private final MatriculaRepository matriculaRepository;

    // ============= APODERADOS =============
    
    @PostMapping("/apoderados")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<String> crearApoderado(@Valid @RequestBody ApoderadoRequest request) {
        try {
            User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            DocumentoIdentidad documento = DocumentoIdentidad.builder()
                .tipoDocumento(request.getDocumentoIdentidad().getTipoDocumento())
                .numeroDocumento(request.getDocumentoIdentidad().getNumeroDocumento())
                .build();

            Apoderado apoderado = Apoderado.builder()
                .documentoIdentidad(documento)
                .user(user)
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .parentesco(request.getParentesco())
                .direccion(request.getDireccion())
                .departamento(request.getDepartamento())
                .provincia(request.getProvincia())
                .distrito(request.getDistrito())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .lugarTrabajo(request.getLugarTrabajo())
                .cargo(request.getCargo())
                .build();

            apoderadoRepository.save(apoderado);
            return ResponseEntity.status(HttpStatus.CREATED).body("Apoderado creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= DOCENTES =============
    
    @PostMapping("/docentes")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<String> crearDocente(@Valid @RequestBody DocenteRequest request) {
        try {
            DocumentoIdentidad documento = DocumentoIdentidad.builder()
                .tipoDocumento(request.getDocumentoIdentidad().getTipoDocumento())
                .numeroDocumento(request.getDocumentoIdentidad().getNumeroDocumento())
                .build();

            Docente docente = Docente.builder()
                .documentoIdentidad(documento)
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .direccion(request.getDireccion())
                .departamento(request.getDepartamento())
                .provincia(request.getProvincia())
                .distrito(request.getDistrito())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .estado(true)
                .build();

            docenteRepository.save(docente);
            return ResponseEntity.status(HttpStatus.CREATED).body("Docente creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= GRADOS =============
    
    @PostMapping("/grados")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<String> crearGrado(@Valid @RequestBody GradoRequest request) {
        try {
            Grado grado = Grado.builder()
                .nombreGrado(request.getNombreGrado())
                .nivel(request.getNivel())
                .build();

            gradoRepository.save(grado);
            return ResponseEntity.status(HttpStatus.CREATED).body("Grado creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= ALUMNOS =============
    
    @PostMapping("/alumnos")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<String> crearAlumno(@Valid @RequestBody AlumnoRequest request) {
        try {
            Apoderado apoderado = apoderadoRepository.findById(request.getApoderadoId())
                .orElseThrow(() -> new RuntimeException("Apoderado no encontrado"));

            DocumentoIdentidad documento = DocumentoIdentidad.builder()
                .tipoDocumento(request.getDocumentoIdentidad().getTipoDocumento())
                .numeroDocumento(request.getDocumentoIdentidad().getNumeroDocumento())
                .build();

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

            alumnoRepository.save(alumno);
            return ResponseEntity.status(HttpStatus.CREATED).body("Alumno creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= AULAS =============
    
    @PostMapping("/aulas")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<String> crearAula(@Valid @RequestBody AulaRequest request) {
        try {
            Grado grado = gradoRepository.findById(request.getGradoId())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

            Docente docente = docenteRepository.findById(request.getDocenteId())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

            Aula aula = Aula.builder()
                .nombre(request.getNombre())
                .grado(grado)
                .docente(docente)
                .capacidad(request.getCapacidad())
                .horarioInicio(request.getHorarioInicio())
                .horarioFin(request.getHorarioFin())
                .build();

            aulaRepository.save(aula);
            return ResponseEntity.status(HttpStatus.CREATED).body("Aula creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= CURSOS =============
    
    @PostMapping("/cursos")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<String> crearCurso(@Valid @RequestBody CursoRequest request) {
        try {
            // Verificar si ya existe un curso con el mismo nombre
            if (cursoRepository.existsByNombre(request.getNombre())) {
                return ResponseEntity.badRequest().body("Ya existe un curso con ese nombre");
            }

            Curso curso = Curso.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .build();

            cursoRepository.save(curso);
            return ResponseEntity.status(HttpStatus.CREATED).body("Curso creado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= GRADO-CURSOS =============
    
    @PostMapping("/grado-cursos")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<String> crearGradoCurso(@Valid @RequestBody GradoCursoRequest request) {
        try {
            Grado grado = gradoRepository.findById(request.getGradoId())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

            Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

            // Verificar si ya existe la asociación
            if (gradoCursoRepository.existsByGradoAndCurso(grado, curso)) {
                return ResponseEntity.badRequest().body("Ya existe la asociación entre este grado y curso");
            }

            GradoCurso gradoCurso = GradoCurso.builder()
                .grado(grado)
                .curso(curso)
                .horasSemanales(request.getHorasSemanales())
                .build();

            gradoCursoRepository.save(gradoCurso);
            return ResponseEntity.status(HttpStatus.CREATED).body("Asociación grado-curso creada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= MÉTODOS DE CONSULTA =============
    
    @GetMapping("/usuarios")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<User>> listarUsuarios() {
        List<User> usuarios = userRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/apoderados")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<Apoderado>> listarApoderados() {
        List<Apoderado> apoderados = apoderadoRepository.findAll();
        return ResponseEntity.ok(apoderados);
    }

    @GetMapping("/docentes")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<Docente>> listarDocentes() {
        List<Docente> docentes = docenteRepository.findAll();
        return ResponseEntity.ok(docentes);
    }

    @GetMapping("/grados")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria', 'Apoderado')")
    public ResponseEntity<List<Grado>> listarGrados() {
        List<Grado> grados = gradoRepository.findAll();
        return ResponseEntity.ok(grados);
    }

    @GetMapping("/alumnos")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<Alumno>> listarAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        return ResponseEntity.ok(alumnos);
    }

    @GetMapping("/aulas")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<Aula>> listarAulas() {
        List<Aula> aulas = aulaRepository.findAll();
        return ResponseEntity.ok(aulas);
    }

    @GetMapping("/cursos")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<CursoResponse>> listarCursos() {
        try {
            List<Curso> cursos = cursoRepository.findAll();
            List<CursoResponse> cursosResponse = cursos.stream()
                .map(curso -> CursoResponse.builder()
                    .id(curso.getId())
                    .nombre(curso.getNombre())
                    .descripcion(curso.getDescripcion())
                    .build())
                .collect(Collectors.toList());
            return ResponseEntity.ok(cursosResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/grado-cursos")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<GradoCursoResponse>> listarGradoCursos() {
        try {
            List<GradoCurso> gradoCursos = gradoCursoRepository.findAllWithDetails();
            List<GradoCursoResponse> gradoCursosResponse = gradoCursos.stream()
                .map(gc -> GradoCursoResponse.builder()
                    .id(gc.getId())
                    .gradoId(gc.getGrado().getId())
                    .gradoNombre(gc.getGrado().getNombreGrado())
                    .nivelEducativo(gc.getGrado().getNivel().name())
                    .cursoId(gc.getCurso().getId())
                    .cursoNombre(gc.getCurso().getNombre())
                    .cursoDescripcion(gc.getCurso().getDescripcion())
                    .horasSemanales(gc.getHorasSemanales())
                    .build())
                .collect(Collectors.toList());
            return ResponseEntity.ok(gradoCursosResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ============= CREACIÓN RÁPIDA DE DATOS DE PRUEBA =============
    
    @PostMapping("/datos-prueba")
    @PreAuthorize("hasAnyAuthority('Administrador')")
    public ResponseEntity<String> crearDatosPrueba() {
        try {
            // Crear grados
            Grado primero = Grado.builder()
                .nombreGrado("1er Grado")
                .nivel(NivelEducativo.Primaria)
                .build();
            gradoRepository.save(primero);

            Grado segundo = Grado.builder()
                .nombreGrado("2do Grado")
                .nivel(NivelEducativo.Primaria)
                .build();
            gradoRepository.save(segundo);

            Grado tercero = Grado.builder()
                .nombreGrado("3er Grado")
                .nivel(NivelEducativo.Primaria)
                .build();
            gradoRepository.save(tercero);

            // Crear cursos
            Curso matematicas = Curso.builder()
                .nombre("Matemática")
                .descripcion("Curso de matemática básica")
                .build();
            cursoRepository.save(matematicas);

            Curso comunicacion = Curso.builder()
                .nombre("Comunicación")
                .descripcion("Curso de comunicación y lenguaje")
                .build();
            cursoRepository.save(comunicacion);

            Curso ciencias = Curso.builder()
                .nombre("Ciencia y Tecnología")
                .descripcion("Curso de ciencias naturales y tecnología")
                .build();
            cursoRepository.save(ciencias);

            Curso personal = Curso.builder()
                .nombre("Personal Social")
                .descripcion("Curso de personal social y ciudadanía")
                .build();
            cursoRepository.save(personal);

            Curso artistica = Curso.builder()
                .nombre("Arte y Cultura")
                .descripcion("Curso de educación artística y cultural")
                .build();
            cursoRepository.save(artistica);

            Curso fisica = Curso.builder()
                .nombre("Educación Física")
                .descripcion("Curso de educación física y deportes")
                .build();
            cursoRepository.save(fisica);

            // Crear asociaciones grado-curso para 1er grado
            List<Curso> cursosBasicos = List.of(matematicas, comunicacion, ciencias, personal, artistica, fisica);
            
            for (Curso curso : cursosBasicos) {
                // Asociar con 1er grado
                GradoCurso gc1 = GradoCurso.builder()
                    .grado(primero)
                    .curso(curso)
                    .horasSemanales(getHorasSemanalesPorCurso(curso.getNombre()))
                    .build();
                gradoCursoRepository.save(gc1);

                // Asociar con 2do grado
                GradoCurso gc2 = GradoCurso.builder()
                    .grado(segundo)
                    .curso(curso)
                    .horasSemanales(getHorasSemanalesPorCurso(curso.getNombre()))
                    .build();
                gradoCursoRepository.save(gc2);

                // Asociar con 3er grado
                GradoCurso gc3 = GradoCurso.builder()
                    .grado(tercero)
                    .curso(curso)
                    .horasSemanales(getHorasSemanalesPorCurso(curso.getNombre()))
                    .build();
                gradoCursoRepository.save(gc3);
            }

            // Crear docente
            DocumentoIdentidad docDocumento = DocumentoIdentidad.builder()
                .tipoDocumento(TipoDocumento.DNI)
                .numeroDocumento("12345678")
                .build();

            Docente docente = Docente.builder()
                .documentoIdentidad(docDocumento)
                .nombre("María")
                .apellido("González")
                .direccion("Av. Educación 123")
                .departamento("Lima")
                .provincia("Lima")
                .distrito("San Isidro")
                .telefono("987654321")
                .email("maria.gonzalez@colegio.edu.pe")
                .estado(true)
                .build();
            docenteRepository.save(docente);

            // Crear aulas
            Aula aula1 = Aula.builder()
                .nombre("Aula A1")
                .grado(primero)
                .docente(docente)
                .capacidad(25)
                .horarioInicio(LocalTime.of(8, 0))
                .horarioFin(LocalTime.of(13, 0))
                .build();
            aulaRepository.save(aula1);

            Aula aula2 = Aula.builder()
                .nombre("Aula A2")
                .grado(segundo)
                .docente(docente)
                .capacidad(25)
                .horarioInicio(LocalTime.of(8, 0))
                .horarioFin(LocalTime.of(13, 0))
                .build();
            aulaRepository.save(aula2);

            // ============= CREAR USUARIO Y APODERADO DE PRUEBA =============
            
            // Crear usuario para apoderado
            User userApoderado = User.builder()
                .username("apoderado_demo")
                .password("$2a$10$2WZj9Y59IqS2qa4rEN2qIOuDGgKZYRpSIQtXuEsba8yVIEPn0U3Ha") // password: "apoderado1"
                .role(Role.Apoderado)
                .estado(true)
                .build();
            userRepository.save(userApoderado);

            // Crear documento para apoderado
            DocumentoIdentidad apoderadoDoc = DocumentoIdentidad.builder()
                .tipoDocumento(TipoDocumento.DNI)
                .numeroDocumento("98765432")
                .build();

            // Crear apoderado
            Apoderado apoderado = Apoderado.builder()
                .documentoIdentidad(apoderadoDoc)
                .user(userApoderado)
                .nombre("Luis")
                .apellido("Rodriguez")
                .parentesco(Parentesco.Padre)
                .direccion("Av. Las Flores 789")
                .departamento("Lima")
                .provincia("Lima")
                .distrito("San Borja")
                .telefono("987123456")
                .email("alonso.lq08@gmail.com")
                .lugarTrabajo("Empresa Demo")
                .cargo("Ingeniero")
                .build();
            apoderadoRepository.save(apoderado);

            // ============= CREAR ALUMNO DE PRUEBA =============
            
            DocumentoIdentidad alumnoDoc = DocumentoIdentidad.builder()
                .tipoDocumento(TipoDocumento.DNI)
                .numeroDocumento("55667788")
                .build();

            Alumno alumno = Alumno.builder()
                .documentoIdentidad(alumnoDoc)
                .apoderado(apoderado)
                .nombre("Sofia")
                .apellido("Rodriguez")
                .fechaNacimiento(LocalDate.of(2018, 5, 20))
                .genero(Genero.Femenino)
                .direccion("Av. Las Flores 789")
                .departamento("Lima")
                .provincia("Lima")
                .distrito("San Borja")
                .tieneDiscapacidad(false)
                .estado(true)
                .build();
            alumnoRepository.save(alumno);

            // ============= CREAR MATRÍCULA DE PRUEBA =============
            
            Matricula matricula = Matricula.builder()
                .alumno(alumno)
                .grado(primero)
                .anoEscolar(Year.now())
                .fechaMatricula(LocalDate.now())
                .tipoMatricula(TipoMatricula.Nueva)
                .estado(EstadoMatricula.Completada)
                .usuarioMatricula(userRepository.findByUsername("Alonso").orElse(null))
                .observaciones("Matrícula de prueba automática")
                .build();
            matriculaRepository.save(matricula);

            // ============= CREAR FECHAS DE PAGO DE PRUEBA =============
            
            crearFechasPagoPrueba(matricula);

            return ResponseEntity.ok("Datos de prueba creados exitosamente (3 grados, 6 cursos, 18 asociaciones grado-curso, 1 docente, 2 aulas, 1 usuario apoderado, 1 apoderado, 1 alumno, 1 matrícula con 10 cuotas de S/ 200.00)");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    private void crearFechasPagoPrueba(Matricula matricula) {
        // Crear 10 cuotas mensuales (marzo a diciembre) con monto de S/ 200.00
        BigDecimal montoCuota = new BigDecimal("200.00");
        
        int numeroCuota = 1;
        for (int mes = 3; mes <= 12; mes++) {
            FechaPago fechaPago = FechaPago.builder()
                .matricula(matricula)
                .descripcion("Cuota " + numeroCuota + "/" + matricula.getAnoEscolar())
                .fechaVencimiento(LocalDate.of(matricula.getAnoEscolar().getValue(), mes, 15))
                .monto(montoCuota)
                .pagado(false)
                .build();
            
            fechaPagoRepository.save(fechaPago);
            numeroCuota++;
        }
    }

    private Integer getHorasSemanalesPorCurso(String nombreCurso) {
        return switch (nombreCurso) {
            case "Matemática" -> 6;
            case "Comunicación" -> 6;
            case "Ciencia y Tecnología" -> 4;
            case "Personal Social" -> 3;
            case "Arte y Cultura" -> 2;
            case "Educación Física" -> 2;
            default -> 2;
        };
    }
    
    // ============= VERIFICAR FECHAS DE PAGO =============
    
    @GetMapping("/fechas-pago/matricula/{matriculaId}")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<FechaPago>> listarFechasPagoPorMatricula(@PathVariable Long matriculaId) {
        try {
            List<FechaPago> fechasPago = fechaPagoRepository.findByMatriculaId(matriculaId);
            return ResponseEntity.ok(fechasPago);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/fechas-pago")
    @PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
    public ResponseEntity<List<FechaPago>> listarTodasLasFechasPago() {
        try {
            List<FechaPago> fechasPago = fechaPagoRepository.findAll();
            return ResponseEntity.ok(fechasPago);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
