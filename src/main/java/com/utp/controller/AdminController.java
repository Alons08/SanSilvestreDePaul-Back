package com.utp.controller;

import com.utp.agregates.request.*;
import com.utp.entity.*;
import com.utp.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
public class AdminController {

    private final ApoderadoRepository apoderadoRepository;
    private final AlumnoRepository alumnoRepository;
    private final DocenteRepository docenteRepository;
    private final GradoRepository gradoRepository;
    private final AulaRepository aulaRepository;
    private final CursoRepository cursoRepository;
    private final GradoCursoRepository gradoCursoRepository;

    // ============= APODERADOS =============
    
    @PutMapping("/apoderados/{id}")
    public ResponseEntity<String> actualizarApoderado(@PathVariable Long id, @Valid @RequestBody ApoderadoRequest request) {
        try {
            Apoderado apoderado = apoderadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apoderado no encontrado"));

            // Actualizar datos
            apoderado.setNombre(request.getNombre());
            apoderado.setApellido(request.getApellido());
            apoderado.setParentesco(request.getParentesco());
            apoderado.setDireccion(request.getDireccion());
            apoderado.setDepartamento(request.getDepartamento());
            apoderado.setProvincia(request.getProvincia());
            apoderado.setDistrito(request.getDistrito());
            apoderado.setTelefono(request.getTelefono());
            apoderado.setEmail(request.getEmail());
            apoderado.setLugarTrabajo(request.getLugarTrabajo());
            apoderado.setCargo(request.getCargo());

            // Actualizar documento si es necesario
            if (request.getDocumentoIdentidad() != null) {
                apoderado.getDocumentoIdentidad().setTipoDocumento(request.getDocumentoIdentidad().getTipoDocumento());
                apoderado.getDocumentoIdentidad().setNumeroDocumento(request.getDocumentoIdentidad().getNumeroDocumento());
            }

            apoderadoRepository.save(apoderado);
            return ResponseEntity.ok("Apoderado actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }



    // ============= DOCENTES =============
    
    @PutMapping("/docentes/{id}")
    public ResponseEntity<String> actualizarDocente(@PathVariable Long id, @Valid @RequestBody DocenteRequest request) {
        try {
            Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

            // Actualizar datos
            docente.setNombre(request.getNombre());
            docente.setApellido(request.getApellido());
            docente.setDireccion(request.getDireccion());
            docente.setDepartamento(request.getDepartamento());
            docente.setProvincia(request.getProvincia());
            docente.setDistrito(request.getDistrito());
            docente.setTelefono(request.getTelefono());
            docente.setEmail(request.getEmail());

            // Actualizar documento si es necesario
            if (request.getDocumentoIdentidad() != null) {
                docente.getDocumentoIdentidad().setTipoDocumento(request.getDocumentoIdentidad().getTipoDocumento());
                docente.getDocumentoIdentidad().setNumeroDocumento(request.getDocumentoIdentidad().getNumeroDocumento());
            }

            docenteRepository.save(docente);
            return ResponseEntity.ok("Docente actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }



    // ============= ALUMNOS =============
    
    @PutMapping("/alumnos/{id}")
    public ResponseEntity<String> actualizarAlumno(@PathVariable Long id, @Valid @RequestBody AlumnoRequest request) {
        try {
            Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

            // Actualizar datos
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

            // Actualizar documento si es necesario
            if (request.getDocumentoIdentidad() != null) {
                alumno.getDocumentoIdentidad().setTipoDocumento(request.getDocumentoIdentidad().getTipoDocumento());
                alumno.getDocumentoIdentidad().setNumeroDocumento(request.getDocumentoIdentidad().getNumeroDocumento());
            }

            alumnoRepository.save(alumno);
            return ResponseEntity.ok("Alumno actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }



    // ============= GRADOS =============
    
    @PutMapping("/grados/{id}")
    public ResponseEntity<String> actualizarGrado(@PathVariable Long id, @Valid @RequestBody GradoRequest request) {
        try {
            Grado grado = gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

            grado.setNombreGrado(request.getNombreGrado());
            grado.setNivel(request.getNivel());

            gradoRepository.save(grado);
            return ResponseEntity.ok("Grado actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/grados/{id}")
    public ResponseEntity<String> eliminarGrado(@PathVariable Long id) {
        try {
            if (!gradoRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            gradoRepository.deleteById(id);
            return ResponseEntity.ok("Grado eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= AULAS =============
    
    @PutMapping("/aulas/{id}")
    public ResponseEntity<String> actualizarAula(@PathVariable Long id, @Valid @RequestBody AulaRequest request) {
        try {
            Aula aula = aulaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

            Grado grado = gradoRepository.findById(request.getGradoId())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

            Docente docente = docenteRepository.findById(request.getDocenteId())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

            aula.setNombre(request.getNombre());
            aula.setGrado(grado);
            aula.setDocente(docente);
            aula.setCapacidad(request.getCapacidad());
            aula.setHorarioInicio(request.getHorarioInicio());
            aula.setHorarioFin(request.getHorarioFin());

            aulaRepository.save(aula);
            return ResponseEntity.ok("Aula actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/aulas/{id}")
    public ResponseEntity<String> eliminarAula(@PathVariable Long id) {
        try {
            if (!aulaRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            aulaRepository.deleteById(id);
            return ResponseEntity.ok("Aula eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= CURSOS =============
    
    @PutMapping("/cursos/{id}")
    public ResponseEntity<String> actualizarCurso(@PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        try {
            Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

            // Verificar que no exista otro curso con el mismo nombre
            if (!curso.getNombre().equals(request.getNombre()) && 
                cursoRepository.existsByNombre(request.getNombre())) {
                return ResponseEntity.badRequest().body("Ya existe un curso con ese nombre");
            }

            curso.setNombre(request.getNombre());
            curso.setDescripcion(request.getDescripcion());

            cursoRepository.save(curso);
            return ResponseEntity.ok("Curso actualizado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<String> eliminarCurso(@PathVariable Long id) {
        try {
            if (!cursoRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            cursoRepository.deleteById(id);
            return ResponseEntity.ok("Curso eliminado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= GRADO-CURSOS =============
    
    @PutMapping("/grado-cursos/{id}")
    public ResponseEntity<String> actualizarGradoCurso(@PathVariable Long id, @Valid @RequestBody GradoCursoRequest request) {
        try {
            GradoCurso gradoCurso = gradoCursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asociación grado-curso no encontrada"));

            Grado grado = gradoRepository.findById(request.getGradoId())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

            Curso curso = cursoRepository.findById(request.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

            // Verificar que no exista otra asociación igual
            if ((!gradoCurso.getGrado().getId().equals(grado.getId()) || 
                 !gradoCurso.getCurso().getId().equals(curso.getId())) &&
                gradoCursoRepository.existsByGradoAndCurso(grado, curso)) {
                return ResponseEntity.badRequest().body("Ya existe la asociación entre este grado y curso");
            }

            gradoCurso.setGrado(grado);
            gradoCurso.setCurso(curso);
            gradoCurso.setHorasSemanales(request.getHorasSemanales());

            gradoCursoRepository.save(gradoCurso);
            return ResponseEntity.ok("Asociación grado-curso actualizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/grado-cursos/{id}")
    public ResponseEntity<String> eliminarGradoCurso(@PathVariable Long id) {
        try {
            if (!gradoCursoRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            gradoCursoRepository.deleteById(id);
            return ResponseEntity.ok("Asociación grado-curso eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
