package com.utp.controller;

import com.utp.entity.Apoderado;
import com.utp.entity.Alumno;
import com.utp.entity.Docente;
import com.utp.repository.ApoderadoRepository;
import com.utp.repository.AlumnoRepository;
import com.utp.repository.DocenteRepository;
import com.utp.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('Administrador', 'Secretaria')")
public class EstadoController {

    private final EstadoService estadoService;
    private final ApoderadoRepository apoderadoRepository;
    private final AlumnoRepository alumnoRepository;
    private final DocenteRepository docenteRepository;

    // ============= CAMBIAR ESTADOS =============
    
    @PutMapping("/apoderados/{id}")
    public ResponseEntity<String> cambiarEstadoApoderado(@PathVariable Long id, @RequestParam Boolean estado) {
        try {
            estadoService.cambiarEstadoApoderado(id, estado);
            String mensaje = estado ? "Apoderado activado exitosamente" : "Apoderado desactivado exitosamente";
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/alumnos/{id}")
    public ResponseEntity<String> cambiarEstadoAlumno(@PathVariable Long id, @RequestParam Boolean estado) {
        try {
            estadoService.cambiarEstadoAlumno(id, estado);
            String mensaje = estado ? "Alumno activado exitosamente" : "Alumno desactivado exitosamente";
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/docentes/{id}")
    public ResponseEntity<String> cambiarEstadoDocente(@PathVariable Long id, @RequestParam Boolean estado) {
        try {
            estadoService.cambiarEstadoDocente(id, estado);
            String mensaje = estado ? "Docente activado exitosamente" : "Docente desactivado exitosamente";
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/alumnos/{alumnoId}/reasignar-apoderado/{apoderadoId}")
    public ResponseEntity<String> reasignarApoderado(@PathVariable Long alumnoId, @PathVariable Long apoderadoId) {
        try {
            estadoService.reasignarApoderado(alumnoId, apoderadoId);
            return ResponseEntity.ok("Apoderado reasignado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // ============= CONSULTAR POR ESTADO =============
    
    @GetMapping("/apoderados")
    public ResponseEntity<List<Apoderado>> listarApoderadosPorEstado(@RequestParam Boolean estado) {
        try {
            List<Apoderado> apoderados = apoderadoRepository.findByEstado(estado);
            return ResponseEntity.ok(apoderados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/alumnos")
    public ResponseEntity<List<Alumno>> listarAlumnosPorEstado(@RequestParam Boolean estado) {
        try {
            List<Alumno> alumnos = alumnoRepository.findByEstado(estado);
            return ResponseEntity.ok(alumnos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/docentes")
    public ResponseEntity<List<Docente>> listarDocentesPorEstado(@RequestParam Boolean estado) {
        try {
            List<Docente> docentes = docenteRepository.findByEstado(estado);
            return ResponseEntity.ok(docentes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/alumnos/sin-apoderado-activo")
    public ResponseEntity<List<Alumno>> listarAlumnosSinApoderadoActivo() {
        try {
            List<Alumno> alumnos = alumnoRepository.findAlumnosSinApoderadoActivo();
            return ResponseEntity.ok(alumnos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/apoderados/activos")
    public ResponseEntity<List<Apoderado>> listarApoderadosActivos() {
        try {
            List<Apoderado> apoderados = apoderadoRepository.findApoderadosActivos();
            return ResponseEntity.ok(apoderados);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
